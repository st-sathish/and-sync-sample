package com.trucontactssync.access.country;

/**
 * Columns which is fixed for all tables
 */
public interface FixedColumnNames {

    /** Object Id primary Key */
    String COLUMN_OBJECT_ID = "objectId";

    /** Track when the data is updated */
    String COLUMN_UPDATED_AT = "updatedAt";

    /** Parse and store the raw json object */
    String COLUMN_ROW_DATA = "rowdata";

    /** Mark the rows as deleted or not */
    String COLUMN_IS_DELETED = "isdeleted";
}
