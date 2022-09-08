package technology.assessment.app.validation;

import technology.assessment.app.exception.BadRequestException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumAccountTypePatternValidator implements ConstraintValidator<EnumAccountTypePattern, Enum<?>> {
    private Pattern pattern;

    @Override
    public void initialize(EnumAccountTypePattern annotation) {
        try {
            pattern = Pattern.compile(annotation.regexp());
        } catch (PatternSyntaxException e) {
            //throw new IllegalArgumentException("Given regex is invalid", e);
            throw new BadRequestException("Given regex is invalid");
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }
}
