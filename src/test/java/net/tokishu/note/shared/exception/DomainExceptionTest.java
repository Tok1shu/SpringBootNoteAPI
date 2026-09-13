package net.tokishu.note.shared.exception;

import net.tokishu.note.shared.exception.model.ParentDomainException;
import net.tokishu.note.shared.exception.model.vo.Error;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;


public class DomainExceptionTest {

    private static final String ERROR_VALUE_REGEX = Error.ERROR_VALUE_FORMAT;

    /*
       Это предложенный вариант от ии что я переписывал руками, так как Error value я вынес в отдельный объект,
       то по идее можно попробовать выслеживать использование именно его? ну точнее его эээ иниацилизацию где либо в коде.
       здесь как я понял мы ищем абсолютно всех ктоооо вообще вызвал точнее extend-нулся на ParentDomainException.
       что как по мне требует больше действий идти по ветке выыыше и выше.
    */


    @Test
    void allDomainExceptionsMustHaveValidErrorValue() {
        // использую рефлекшен впервые, надо бы изучить подробнее как им пользоватсья
        Reflections reflections = new Reflections("net.tokishu.note");
        Set<Class<? extends ParentDomainException>> exceptionClasses =
                reflections.getSubTypesOf(ParentDomainException.class);

        for (Class<? extends ParentDomainException> clazz : exceptionClasses) {

            if (Modifier.isAbstract(clazz.getModifiers())) continue;


            ParentDomainException ex = instanceForTest(clazz);
            String errorValue = ex.getError();

            if (!errorValue.matches(ERROR_VALUE_REGEX)) {
                throw new RuntimeException("Class: " + clazz.getSimpleName() + " has invalid error value: "
                        + errorValue + ". It must contains regex: " + ERROR_VALUE_REGEX + "");
            }
        }

    }

    private ParentDomainException instanceForTest(Class<? extends ParentDomainException> clazz) {
        try {
            Constructor<?>[] constructors = clazz.getConstructors();
            Constructor<?> ctor = constructors[0];

            Object[] args = new Object[ctor.getParameterCount()];
            return (ParentDomainException) ctor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance for test: " + clazz.getName());
        }
    }

}
