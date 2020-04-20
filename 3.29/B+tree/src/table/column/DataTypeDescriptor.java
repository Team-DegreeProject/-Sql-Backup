package table.column;

import table.type.SqlConstant;

public class DataTypeDescriptor implements SqlConstant {

    public DataTypeDescriptor() {
    }

    public DataTypeDescriptor(int typeId) {
        this.typeId = typeId;
    }

    public DataTypeDescriptor(int typeId, boolean isNullable) {
        this.typeId = typeId;
        this.isNullable = isNullable;
    }

    public int typeId;
    private int precision;
    private int scale;
    private boolean isNullable=true;
    private int maximumWidth;

    public DataTypeDescriptor(int typeId, int precision, int scale, boolean isNullable, int maximumWidth) {
        this.typeId = typeId;
        this.precision = precision;
        this.scale = scale;
        this.isNullable = isNullable;
        this.maximumWidth = maximumWidth;
    }




    public int getMaximumWidth() {
        return maximumWidth;
    }

    /**
     * Returns the number of decimal digits for the datatype, if applicable.
     *
     * @return The number of decimal digits for the datatype.  Returns
     * zero for non-numeric datatypes.
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Returns the number of digits to the right of the decimal for
     * the datatype, if applicable.
     *
     * @return The number of digits to the right of the decimal for
     * the datatype.  Returns zero for non-numeric datatypes.
     */
    public int getScale() {
        return scale;
    }



    /**
     * Returns TRUE if the datatype can contain NULL, FALSE if not.
     * I assume we will never have columns where the nullability is unknown.
     *
     * @return TRUE if the datatype can contain NULL, FALSE if not.
     */
    public boolean isNullable() {
        return isNullable;
    }


    /**
     * Get the type Id stored within this type descriptor.
     */
    public int getTypeId() {
        return typeId;
    }



    @Override
    public String toString() {
        return tokenImage[typeId];
    }

}

