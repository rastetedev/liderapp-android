package com.hk.kbottomnavigation.verticalstepperform;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;

import com.hk.kbottomnavigation.R;
import com.hk.kbottomnavigation.verticalstepperform.interfaces.VerticalStepperForm;
import com.hk.kbottomnavigation.verticalstepperform.utils.Animations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom layout that implements a vertical stepper form
 */
public class VerticalStepperFormLayout extends RelativeLayout implements View.OnClickListener {

    // Style
    protected float alphaOfDisabledElements;
    protected int stepNumberBackgroundColor;
    protected int buttonBackgroundColor;
    protected int buttonPressedBackgroundColor;
    protected int stepNumberTextColor;
    protected int buttonTextColor;
    protected int buttonPressedTextColor;
    protected int errorMessageTextColor;
    protected boolean displayBottomNavigation;

    // Views
    protected LayoutInflater mInflater;
    protected LinearLayout content;
    protected ScrollView stepsScrollView;
    protected List<LinearLayout> stepLayouts;
    protected List<View> stepContentViews;
    protected AppCompatButton confirmationButton;
    protected ProgressBar progressBar;
    protected AppCompatImageButton previousStepButton, nextStepButton;
    protected RelativeLayout bottomNavigation;

    // Data
    protected List<String> steps;

    // Logic
    protected int activeStep = 0;
    protected int numberOfSteps;
    protected boolean[] completedSteps;

    // Listeners and callbacks
    protected VerticalStepperForm verticalStepperFormImplementation;

    // Context
    protected Context context;
    protected Activity activity;

    public VerticalStepperFormLayout(Context context) {
        super(context);
        init(context);
    }

    public VerticalStepperFormLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalStepperFormLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.vertical_stepper_form_layout, this, true);
    }

    public int getActiveStepNumber() {
        return activeStep;
    }

    public void setActiveStepAsCompleted() {
        setStepAsCompleted(activeStep);
    }

    public void setActiveStepAsUncompleted(String errorMessage) {
        setStepAsUncompleted(activeStep, errorMessage);
    }

    public void setStepAsCompleted(int stepNumber) {
        completedSteps[stepNumber] = true;

        LinearLayout stepLayout = stepLayouts.get(stepNumber);
        RelativeLayout stepHeader = (RelativeLayout) stepLayout.findViewById(R.id.step_header);
        ImageView stepDone = (ImageView) stepHeader.findViewById(R.id.step_done);
        TextView stepNumberTextView = (TextView) stepHeader.findViewById(R.id.step_number);
        LinearLayout errorContainer = (LinearLayout) stepLayout.findViewById(R.id.error_container);
        TextView errorTextView = (TextView) errorContainer.findViewById(R.id.error_message);
        AppCompatButton nextButton = (AppCompatButton) stepLayout.findViewById(R.id.next_step);

        stepHeader.setAlpha(1);

        nextButton.setClickable(true);
        nextButton.setAlpha(1);

        if (stepNumber != activeStep) {
            stepDone.setVisibility(View.VISIBLE);
            stepNumberTextView.setVisibility(View.INVISIBLE);
        } else {
            if (stepNumber != numberOfSteps) {
                enableNextButtonInBottomNavigationLayout();
            } else {
                disableNextButtonInBottomNavigationLayout();
            }
        }

        errorTextView.setText("");
        //errorContainer.setVisibility(View.GONE);
        Animations.slideUp(errorContainer);

        displayCurrentProgress();
    }

    public void setStepAsUncompleted(int stepNumber, String errorMessage) {
        completedSteps[stepNumber] = false;

        LinearLayout stepLayout = stepLayouts.get(stepNumber);
        RelativeLayout stepHeader = (RelativeLayout) stepLayout.findViewById(R.id.step_header);
        ImageView stepDone = (ImageView) stepHeader.findViewById(R.id.step_done);
        TextView stepNumberTextView = (TextView) stepHeader.findViewById(R.id.step_number);
        AppCompatButton nextButton = (AppCompatButton) stepLayout.findViewById(R.id.next_step);

        stepDone.setVisibility(View.INVISIBLE);
        stepNumberTextView.setVisibility(View.VISIBLE);

        nextButton.setClickable(false);
        nextButton.setAlpha(alphaOfDisabledElements);

        if (stepNumber == activeStep) {
            disableNextButtonInBottomNavigationLayout();
        } else {
            stepHeader.setAlpha(alphaOfDisabledElements);
        }

        if (stepNumber < numberOfSteps) {
            setStepAsUncompleted(numberOfSteps, null);
        }

        if (errorMessage != null && !errorMessage.equals("")) {
            LinearLayout errorContainer = (LinearLayout) stepLayout.findViewById(R.id.error_container);
            TextView errorTextView = (TextView) errorContainer.findViewById(R.id.error_message);

            errorTextView.setText(errorMessage);
            //errorContainer.setVisibility(View.VISIBLE);
            Animations.slideDown(errorContainer);
        }

        displayCurrentProgress();
    }

    public boolean isActiveStepCompleted() {
        return isStepCompleted(activeStep);
    }

    public boolean isStepCompleted(int stepNumber) {
        return completedSteps[stepNumber];
    }

    public boolean isAnyStepCompleted() {
        for (boolean completedStep : completedSteps) {
            if (completedStep) {
                return true;
            }
        }
        return false;
    }

    public boolean arePreviousStepsCompleted(int stepNumber) {
        boolean previousStepsAreCompleted = true;
        for (int i = (stepNumber - 1); i >= 0 && previousStepsAreCompleted; i--) {
            previousStepsAreCompleted = completedSteps[i];
        }
        return previousStepsAreCompleted;
    }

    public void goToNextStep() {
        goToStep(activeStep + 1, false);
    }

    public void goToPreviousStep() {
        goToStep(activeStep - 1, false);
    }

    public void goToStep(int stepNumber, boolean restoration) {
        if (activeStep != stepNumber || restoration) {
            hideSoftKeyboard();
            boolean previousStepsAreCompleted =
                    arePreviousStepsCompleted(stepNumber);
            if (stepNumber == 0 || previousStepsAreCompleted) {
                openStep(stepNumber, restoration);
            }
        }
    }

    @Deprecated
    public void setActiveStepAsUncompleted() {
        setStepAsUncompleted(activeStep, null);
    }

    @Deprecated
    public void setStepAsUncompleted(int stepNumber) {
        setStepAsUncompleted(stepNumber, null);
    }

    @Deprecated
    public void initialiseVerticalStepperForm(String[] stepsNames,
                                              int colorPrimary, int colorPrimaryDark,
                                              VerticalStepperForm verticalStepperForm,
                                              Activity activity) {

        this.alphaOfDisabledElements = 0.25f;
        this.buttonTextColor = Color.rgb(255, 255, 255);
        this.buttonPressedTextColor = Color.rgb(255, 255, 255);
        this.stepNumberTextColor = Color.rgb(255, 255, 255);
        this.stepNumberBackgroundColor = colorPrimary;
        this.buttonBackgroundColor = colorPrimary;
        this.buttonPressedBackgroundColor = colorPrimaryDark;
        this.errorMessageTextColor = Color.rgb(175, 18, 18);
        this.displayBottomNavigation = true;

        this.verticalStepperFormImplementation = verticalStepperForm;
        this.activity = activity;

        initStepperForm(stepsNames);
    }

    @Deprecated
    public void initialiseVerticalStepperForm(String[] stepsNames,
                                              int buttonBackgroundColor, int buttonTextColor,
                                              int buttonPressedBackgroundColor, int buttonPressedTextColor,
                                              int stepNumberBackgroundColor, int stepNumberTextColor,
                                              VerticalStepperForm verticalStepperForm,
                                              Activity activity) {

        this.alphaOfDisabledElements = 0.25f;
        this.buttonBackgroundColor = buttonBackgroundColor;
        this.buttonTextColor = buttonTextColor;
        this.buttonPressedBackgroundColor = buttonPressedBackgroundColor;
        this.buttonPressedTextColor = buttonPressedTextColor;
        this.stepNumberBackgroundColor = stepNumberBackgroundColor;
        this.stepNumberTextColor = stepNumberTextColor;
        this.errorMessageTextColor = Color.rgb(175, 18, 18);
        this.displayBottomNavigation = true;

        this.verticalStepperFormImplementation = verticalStepperForm;
        this.activity = activity;

        initStepperForm(stepsNames);
    }

    protected void initialiseVerticalStepperForm(Builder builder) {

        this.verticalStepperFormImplementation = builder.verticalStepperFormImplementation;
        this.activity = builder.activity;

        this.alphaOfDisabledElements = builder.alphaOfDisabledElements;
        this.stepNumberBackgroundColor = builder.stepNumberBackgroundColor;
        this.buttonBackgroundColor = builder.buttonBackgroundColor;
        this.buttonPressedBackgroundColor = builder.buttonPressedBackgroundColor;
        this.stepNumberTextColor = builder.stepNumberTextColor;
        this.buttonTextColor = builder.buttonTextColor;
        this.buttonPressedTextColor = builder.buttonPressedTextColor;
        this.errorMessageTextColor = builder.errorMessageTextColor;
        this.displayBottomNavigation = builder.displayBottomNavigation;

        initStepperForm(builder.steps);
    }

    protected void initStepperForm(String[] stepsNames) {
        setSteps(stepsNames);

        List<View> stepContentLayouts = new ArrayList<>();
        for (int i = 0; i < numberOfSteps; i++) {
            View stepLayout = verticalStepperFormImplementation.createStepContentView(i);
            stepContentLayouts.add(stepLayout);
        }
        stepContentViews = stepContentLayouts;

        initializeForm();

        verticalStepperFormImplementation.onStepOpening(activeStep);
    }

    protected void setSteps(String[] steps) {
        this.steps = new ArrayList<>(Arrays.asList(steps));
        numberOfSteps = steps.length;
        setAuxVars();
        addConfirmationStepToStepsList();
    }

    protected void registerListeners() {
        previousStepButton.setOnClickListener(this);
        nextStepButton.setOnClickListener(this);
    }

    protected void initializeForm() {
        setUpSteps();
        if (!displayBottomNavigation) {
            hideBottomNavigation();
        }
        goToStep(0, true);

        setObserverForKeyboard();
    }

    // http://stackoverflow.com/questions/2150078/how-to-check-visibility-of-software-keyboard-in-android
    protected void setObserverForKeyboard() {
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                content.getWindowVisibleDisplayFrame(r);

                int heightDiff = content.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, it is probably a keyboard...
                    scrollToActiveStep(true);
                }
            }
        });
    }

    protected void hideBottomNavigation() {
        bottomNavigation.setVisibility(View.GONE);
    }

    protected void setUpSteps() {
        stepLayouts = new ArrayList<>();
        // Set up normal steps
        for (int i = 0; i < numberOfSteps; i++) {
            setUpStep(i);
        }
        // Set up confirmation step
        setUpStep(numberOfSteps);
    }

    protected void setUpStep(int stepNumber) {
        LinearLayout stepLayout = createStepLayout(stepNumber);
        if (stepNumber < numberOfSteps) {
            // The content of the step is the corresponding custom view previously created
            RelativeLayout stepContent = (RelativeLayout) stepLayout.findViewById(R.id.step_content);
            stepContent.addView(stepContentViews.get(stepNumber));
        } else {
            setUpStepLayoutAsConfirmationStepLayout(stepLayout);
        }
        addStepToContent(stepLayout);
    }

    protected void addStepToContent(LinearLayout stepLayout) {
        content.addView(stepLayout);
    }

    protected void setUpStepLayoutAsConfirmationStepLayout(LinearLayout stepLayout) {
        LinearLayout stepLeftLine = (LinearLayout) stepLayout.findViewById(R.id.vertical_line);
        confirmationButton = (AppCompatButton) stepLayout.findViewById(R.id.next_step);

        stepLeftLine.setVisibility(View.INVISIBLE);

        confirmationButton.setClickable(false);
        confirmationButton.setAlpha(alphaOfDisabledElements);

        confirmationButton.setText(R.string.vertical_form_stepper_form_confirm_button);
        confirmationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareSendingAndSend();
            }
        });

        // Some content could be added to the final step inside stepContent layout
        // RelativeLayout stepContent = (RelativeLayout) stepLayout.findViewById(R.id.step_content);
    }

    protected LinearLayout createStepLayout(final int stepNumber) {
        LinearLayout stepLayout = generateStepLayout();

        LinearLayout circle = (LinearLayout) stepLayout.findViewById(R.id.circle);
        Drawable bg = ContextCompat.getDrawable(context, R.drawable.circle_step_done);
        bg.setColorFilter(new PorterDuffColorFilter(
                stepNumberBackgroundColor, PorterDuff.Mode.SRC_IN));
        circle.setBackground(bg);

        TextView stepTitle = (TextView) stepLayout.findViewById(R.id.step_title);
        stepTitle.setText(steps.get(stepNumber));

        TextView stepNumberTextView = (TextView) stepLayout.findViewById(R.id.step_number);
        stepNumberTextView.setText(String.valueOf(stepNumber + 1));
        stepNumberTextView.setTextColor(stepNumberTextColor);

        ImageView stepDoneImageView = (ImageView) stepLayout.findViewById(R.id.step_done);
        stepDoneImageView.setColorFilter(stepNumberTextColor);

        TextView errorMessage = (TextView) stepLayout.findViewById(R.id.error_message);
        ImageView errorIcon = (ImageView) stepLayout.findViewById(R.id.error_icon);
        errorMessage.setTextColor(errorMessageTextColor);
        errorIcon.setColorFilter(errorMessageTextColor);

        RelativeLayout stepHeader = (RelativeLayout) stepLayout.findViewById(R.id.step_header);
        stepHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep(stepNumber, false);
            }
        });

        AppCompatButton nextButton = (AppCompatButton) stepLayout.findViewById(R.id.next_step);
        setButtonColor(nextButton,
                buttonBackgroundColor, buttonTextColor, buttonPressedBackgroundColor, buttonPressedTextColor);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep((stepNumber + 1), false);
            }
        });

        stepLayouts.add(stepLayout);

        return stepLayout;
    }

    protected LinearLayout generateStepLayout() {
        LayoutInflater inflater = LayoutInflater.from(context);
        return (LinearLayout) inflater.inflate(R.layout.step_layout, content, false);
    }

    protected void openStep(int stepNumber, boolean restoration) {
        if (stepNumber >= 0 && stepNumber <= numberOfSteps) {
            activeStep = stepNumber;

            if (stepNumber == 0) {
                disablePreviousButtonInBottomNavigationLayout();
            } else {
                enablePreviousButtonInBottomNavigationLayout();
            }

            if (completedSteps[stepNumber] && activeStep != numberOfSteps) {
                enableNextButtonInBottomNavigationLayout();
            } else {
                disableNextButtonInBottomNavigationLayout();
            }

            for(int i = 0; i <= numberOfSteps; i++) {
                if(i != stepNumber) {
                    disableStepLayout(i, !restoration);
                } else {
                    enableStepLayout(i, !restoration);
                }
            }

            scrollToActiveStep(!restoration);

            if (stepNumber == numberOfSteps) {
                setStepAsCompleted(stepNumber);
            }

            verticalStepperFormImplementation.onStepOpening(stepNumber);
        }
    }

    protected void scrollToStep(final int stepNumber, boolean smoothScroll) {
        if (smoothScroll) {
            stepsScrollView.post(new Runnable() {
                public void run() {
                    stepsScrollView.smoothScrollTo(0, stepLayouts.get(stepNumber).getTop());
                }
            });
        } else {
            stepsScrollView.post(new Runnable() {
                public void run() {
                    stepsScrollView.scrollTo(0, stepLayouts.get(stepNumber).getTop());
                }
            });
        }
    }

    protected void scrollToActiveStep(boolean smoothScroll) {
        scrollToStep(activeStep, smoothScroll);
    }

    protected void findViews() {
        content = (LinearLayout) findViewById(R.id.content);
        stepsScrollView = (ScrollView) findViewById(R.id.steps_scroll);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        previousStepButton = (AppCompatImageButton) findViewById(R.id.down_previous);
        nextStepButton = (AppCompatImageButton) findViewById(R.id.down_next);
        bottomNavigation = (RelativeLayout) findViewById(R.id.bottom_navigation);
    }

    protected void disableStepLayout(int stepNumber, boolean smoothieDisabling) {
        LinearLayout stepLayout = stepLayouts.get(stepNumber);
        RelativeLayout stepHeader = (RelativeLayout) stepLayout.findViewById(R.id.step_header);
        ImageView stepDone = (ImageView) stepHeader.findViewById(R.id.step_done);
        TextView stepNumberTextView = (TextView) stepHeader.findViewById(R.id.step_number);
        LinearLayout button = (LinearLayout) stepLayout.findViewById(R.id.next_step_button_container);
        RelativeLayout stepContent = (RelativeLayout) stepLayout.findViewById(R.id.step_content);

        if (smoothieDisabling) {
            Animations.slideUp(button);
            Animations.slideUp(stepContent);
        } else {
            button.setVisibility(View.GONE);
            stepContent.setVisibility(View.GONE);
        }

        if (!completedSteps[stepNumber]) {
            stepHeader.setAlpha(alphaOfDisabledElements);
            stepDone.setVisibility(View.INVISIBLE);
            stepNumberTextView.setVisibility(View.VISIBLE);
        } else {
            stepHeader.setAlpha(1);
            stepDone.setVisibility(View.VISIBLE);
            stepNumberTextView.setVisibility(View.INVISIBLE);
        }
    }

    protected void enableStepLayout(int stepNumber, boolean smoothieEnabling) {
        LinearLayout stepLayout = stepLayouts.get(stepNumber);
        RelativeLayout stepContent = (RelativeLayout) stepLayout.findViewById(R.id.step_content);
        RelativeLayout stepHeader = (RelativeLayout) stepLayout.findViewById(R.id.step_header);
        ImageView stepDone = (ImageView) stepHeader.findViewById(R.id.step_done);
        TextView stepNumberTextView = (TextView) stepHeader.findViewById(R.id.step_number);
        LinearLayout button = (LinearLayout) stepLayout.findViewById(R.id.next_step_button_container);

        stepHeader.setAlpha(1);

        if (smoothieEnabling) {
            Animations.slideDown(stepContent);
            Animations.slideDown(button);
        } else {
            stepContent.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }

        if (completedSteps[stepNumber] && activeStep != stepNumber) {
            stepDone.setVisibility(View.VISIBLE);
            stepNumberTextView.setVisibility(View.INVISIBLE);
        } else {
            stepDone.setVisibility(View.INVISIBLE);
            stepNumberTextView.setVisibility(View.VISIBLE);
        }
    }

    protected void displayCurrentProgress() {
        int progress = 0;
        for (int i = 0; i < (completedSteps.length - 1); i++) {
            if (completedSteps[i]) {
                ++progress;
            }
        }
        progressBar.setProgress(progress);
    }

    protected void displayMaxProgress() {
        setProgress(numberOfSteps + 1);
    }

    protected void setAuxVars() {
        completedSteps = new boolean[numberOfSteps + 1];
        for (int i = 0; i < (numberOfSteps + 1); i++) {
            completedSteps[i] = false;
        }
        progressBar.setMax(numberOfSteps + 1);
    }

    protected void addConfirmationStepToStepsList() {
        String confirmationStepText = context.getString(R.string.vertical_form_stepper_form_last_step);
        steps.add(confirmationStepText);
    }

    protected void disablePreviousButtonInBottomNavigationLayout() {
        disableBottomButtonNavigation(previousStepButton);
    }

    protected void enablePreviousButtonInBottomNavigationLayout() {
        enableBottomButtonNavigation(previousStepButton);
    }

    protected void disableNextButtonInBottomNavigationLayout() {
        disableBottomButtonNavigation(nextStepButton);
    }

    protected void enableNextButtonInBottomNavigationLayout() {
        enableBottomButtonNavigation(nextStepButton);
    }

    protected void enableBottomButtonNavigation(ImageButton button) {
        button.setAlpha(1f);
        button.setClickable(true);
    }

    protected void disableBottomButtonNavigation(ImageButton button) {
        button.setAlpha(alphaOfDisabledElements);
        button.setClickable(false);
    }

    protected void setProgress(int progress) {
        if (progress > 0 && progress <= (numberOfSteps + 1)) {
            progressBar.setProgress(progress);
        }
    }

    protected void disableConfirmationButton() {
        confirmationButton.setClickable(false);
        confirmationButton.setAlpha(alphaOfDisabledElements);
    }

    protected void hideSoftKeyboard() {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void prepareSendingAndSend() {
        displayDoneIconInConfirmationStep();
        disableConfirmationButton();
        displayMaxProgress();
        verticalStepperFormImplementation.sendData();
    }

    protected void displayDoneIconInConfirmationStep() {
        LinearLayout confirmationStepLayout = stepLayouts.get(stepLayouts.size() - 1);
        ImageView stepDone = (ImageView) confirmationStepLayout.findViewById(R.id.step_done);
        TextView stepNumberTextView = (TextView) confirmationStepLayout.findViewById(R.id.step_number);
        stepDone.setVisibility(View.VISIBLE);
        stepNumberTextView.setVisibility(View.INVISIBLE);
    }

    protected void restoreFormState() {
        goToStep(activeStep, true);
        displayCurrentProgress();
    }

    protected void setButtonColor(AppCompatButton button, int buttonColor, int buttonTextColor,
                                  int buttonPressedColor, int buttonPressedTextColor) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{}
        };
        ColorStateList buttonColours = new ColorStateList(
                states,
                new int[]{
                        buttonPressedColor,
                        buttonPressedColor,
                        buttonColor
                });
        ColorStateList buttonTextColours = new ColorStateList(
                states,
                new int[]{
                        buttonPressedTextColor,
                        buttonPressedTextColor,
                        buttonTextColor
                });
        button.setBackgroundTintList(buttonColours);
        button.setTextColor(buttonTextColours);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        findViews();
        registerListeners();
    }

    @Override
    public void onClick(View v) {
        String previousNavigationButtonTag =
                context.getString(R.string.vertical_form_stepper_form_down_previous);
        if (v.getTag().equals(previousNavigationButtonTag)) {
            goToPreviousStep();
        } else {
            if (isActiveStepCompleted()) {
                goToNextStep();
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("activeStep", this.activeStep);
        bundle.putBooleanArray("completedSteps", this.completedSteps);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) // implicit null check
        {
            Bundle bundle = (Bundle) state;
            this.activeStep = bundle.getInt("activeStep");
            this.completedSteps = bundle.getBooleanArray("completedSteps");
            state = bundle.getParcelable("superState");
            restoreFormState();
        }
        super.onRestoreInstanceState(state);
    }

    public static class Builder {

        // Required parameters
        protected VerticalStepperFormLayout verticalStepperFormLayout;
        protected String[] steps;
        protected VerticalStepperForm verticalStepperFormImplementation;
        protected Activity activity;

        // Optional parameters
        protected float alphaOfDisabledElements = 0.25f;
        protected int stepNumberBackgroundColor = Color.rgb(63, 81, 181);
        protected int buttonBackgroundColor = Color.rgb(63, 81, 181);
        protected int buttonPressedBackgroundColor = Color.rgb(48, 63, 159);
        protected int stepNumberTextColor = Color.rgb(255, 255, 255);
        protected int buttonTextColor = Color.rgb(255, 255, 255);
        protected int buttonPressedTextColor = Color.rgb(255, 255, 255);
        protected int errorMessageTextColor = Color.rgb(175, 18, 18);
        protected boolean displayBottomNavigation = true;

        protected Builder(VerticalStepperFormLayout stepperLayout,
                          String[] steps,
                          VerticalStepperForm stepperImplementation,
                          Activity activity) {

            this.verticalStepperFormLayout = stepperLayout;
            this.steps = steps;
            this.verticalStepperFormImplementation = stepperImplementation;
            this.activity = activity;
        }

        public static Builder newInstance(VerticalStepperFormLayout stepperLayout,
                                          String[] stepNames,
                                          VerticalStepperForm stepperImplementation,
                                          Activity activity) {

            return new Builder(stepperLayout, stepNames, stepperImplementation, activity);
        }

        public Builder primaryColor(int colorPrimary) {
            this.stepNumberBackgroundColor = colorPrimary;
            this.buttonBackgroundColor = colorPrimary;
            return this;
        }

        public Builder primaryDarkColor(int colorPrimaryDark) {
            this.buttonPressedBackgroundColor = colorPrimaryDark;
            return this;
        }

        public Builder stepNumberBackgroundColor(int stepNumberBackgroundColor) {
            this.stepNumberBackgroundColor = stepNumberBackgroundColor;
            return this;
        }

        public Builder buttonBackgroundColor(int buttonBackgroundColor) {
            this.buttonBackgroundColor = buttonBackgroundColor;
            return this;
        }

        public Builder buttonPressedBackgroundColor(int buttonPressedBackgroundColor) {
            this.buttonPressedBackgroundColor = buttonPressedBackgroundColor;
            return this;
        }

        public Builder stepNumberTextColor(int stepNumberTextColor) {
            this.stepNumberTextColor = stepNumberTextColor;
            return this;
        }

        public Builder buttonTextColor(int buttonTextColor) {
            this.buttonTextColor = buttonTextColor;
            return this;
        }

        public Builder buttonPressedTextColor(int buttonPressedTextColor) {
            this.buttonPressedTextColor = buttonPressedTextColor;
            return this;
        }

        public Builder errorMessageTextColor(int errorMessageTextColor) {
            this.errorMessageTextColor = errorMessageTextColor;
            return this;
        }

        public Builder displayBottomNavigation(boolean displayBottomNavigationBar) {
            this.displayBottomNavigation = displayBottomNavigationBar;
            return this;
        }

        public Builder alphaOfDisabledElements(float alpha) {
            this.alphaOfDisabledElements = alpha;
            return this;
        }

        public void init() {
            verticalStepperFormLayout.initialiseVerticalStepperForm(this);
        }
    }
}