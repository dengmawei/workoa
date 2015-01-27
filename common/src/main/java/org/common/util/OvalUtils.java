package org.common.util;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.common.constant.ExceptionConst;
import org.common.exception.ParamsValidatorException;

/**
 * @author : zch
 *         Date: 13-8-22
 *         Time: 下午3:38
 */
public class OvalUtils {
    /**
     * 对接收参数属性的校验
     *
     * @param cmd
     * @throws 参数异常
     */
    public static void paramsValidator(Object cmd) throws ParamsValidatorException {
        Validator validator = new Validator();
        List<ConstraintViolation> ret = validator.validate(cmd);
        if (ret != null && ret.size() > 0) {
            ConstraintViolation cv = ret.get(0);
            throw new ParamsValidatorException(ExceptionConst.PARAM_EXCEPTION.code, cv.getMessage());
        }
    }
}
