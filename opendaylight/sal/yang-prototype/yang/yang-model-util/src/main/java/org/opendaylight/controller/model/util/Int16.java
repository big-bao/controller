/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.model.util;

import java.util.List;

import org.opendaylight.controller.model.api.type.IntegerTypeDefinition;
import org.opendaylight.controller.model.api.type.RangeConstraint;
import org.opendaylight.controller.yang.common.QName;

/**
 * Implementation of Yang int16 built-in type. <br>
 * int16 represents integer values between -32768 and 32767, inclusively. The
 * Java counterpart of Yang int16 built-in type is {@link Short}.
 * 
 * @see AbstractSignedInteger
 */
public class Int16 extends AbstractSignedInteger {

    private static final QName name = BaseTypes.constructQName("int16");
    private Short defaultValue = null;
    private static final String description = 
            "int16  represents integer values between -32768 and 32767, inclusively.";

    public Int16() {
        super(name, description, Short.MIN_VALUE, Short.MAX_VALUE, "");
    }

    public Int16(final List<RangeConstraint> rangeStatements,
            final String units, final Short defaultValue) {
        super(name, description, rangeStatements, units);
        this.defaultValue = defaultValue;
    }

    @Override
    public IntegerTypeDefinition getBaseType() {
        return this;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((defaultValue == null) ? 0 : defaultValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Int16 other = (Int16) obj;
        if (defaultValue == null) {
            if (other.defaultValue != null) {
                return false;
            }
        } else if (!defaultValue.equals(other.defaultValue)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Int16 [defaultValue=");
        builder.append(defaultValue);
        builder.append(", AbstractInteger=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
