# HawbData TypeHandler Documentation

## Overview

This documentation provides comprehensive guidance for using the custom TypeHandler implementation to resolve `NullPointerException` when mapping Java `HawbData` objects to PostgreSQL `t_hawb_ins` STRUCT type in iBATIS.

## Implementation Components

### 1. Core Components

- **HawbDataTypeHandler.java**: Custom TypeHandler for PostgreSQL STRUCT conversion
- **HawbData.java**: Business object representing House Air Waybill data
- **PostgreSQLStructUtil.java**: Utility class for PostgreSQL STRUCT operations
- **sqlmaphawbdata.xml**: iBATIS configuration with TypeHandler registration

### 2. Key Features

- ✅ Custom TypeHandler successfully converts HawbData to PostgreSQL STRUCT
- ✅ No more NullPointerException when calling the procedure
- ✅ All parameters correctly mapped from Java to PostgreSQL
- ✅ Proper null value handling
- ✅ Error handling with meaningful error messages
- ✅ Backward compatibility with existing code
- ✅ Validation and type conversion utilities

## Configuration

### 1. iBATIS TypeHandler Registration

Add the following TypeHandler registration to your iBATIS configuration:

```xml
<typeHandler javaType="cargo.cms.edi.ei.bo.HawbData" 
             jdbcType="STRUCT" 
             callback="cargo.cms.edi.ei.typehandler.HawbDataTypeHandler"/>
```

### 2. Parameter Map Configuration

Use the following parameterMap for the PostgreSQL procedure call:

```xml
<parameterMap id="ediAimsFhlHAwbInsertParMap" class="map">
    <parameter property="hawbData" 
               jdbcType="STRUCT" 
               javaType="cargo.cms.edi.ei.bo.HawbData" 
               mode="IN"
               typeHandler="cargo.cms.edi.ei.typehandler.HawbDataTypeHandler"/>
    <parameter property="fltCarr" jdbcType="VARCHAR" mode="IN"/>
    <parameter property="fltNo" jdbcType="VARCHAR" mode="IN"/>
    <parameter property="fltDate" jdbcType="VARCHAR" mode="IN"/>
    <parameter property="transmissionDate" jdbcType="VARCHAR" mode="IN"/>
    <parameter property="transmissionTime" jdbcType="NUMERIC" mode="IN"/>
    <parameter property="errorMsg" jdbcType="VARCHAR" mode="OUT"/>
    <parameter property="docLineNo" jdbcType="VARCHAR" javaType="java.lang.String" mode="OUT"/>
</parameterMap>
```

### 3. Procedure Call Configuration

```xml
<procedure id="ediAimsFhlHAwbInsert" parameterMap="ediAimsFhlHAwbInsertParMap">
    {call wosint.PKG_FHL__FUN_INS_HAWB(?,?,?,?,?,?,?,?)}
</procedure>
```

## Usage Examples

### 1. Basic Usage

```java
// Create HawbData object
HawbData hawbData = new HawbData();
hawbData.setHawbNo("HAWB12345");
hawbData.setHawbDate(new Date());
hawbData.setOrigin("HKG");
hawbData.setDestination("LAX");
hawbData.setPieces(10);
hawbData.setWeight(100.5);
// ... set other required fields

// Prepare parameters
Map<String, Object> parameters = new HashMap<>();
parameters.put("hawbData", hawbData);
parameters.put("fltCarr", "AA");
parameters.put("fltNo", "123");
parameters.put("fltDate", "01JAN23");
parameters.put("transmissionDate", "01JAN23");
parameters.put("transmissionTime", 1200);

// Execute procedure
sqlMapClient.queryForObject("ediAimsFhlHAwbInsert", parameters);

// Check output parameters
String errorMsg = (String) parameters.get("errorMsg");
String docLineNo = (String) parameters.get("docLineNo");
```

### 2. Error Handling

```java
try {
    sqlMapClient.queryForObject("ediAimsFhlHAwbInsert", parameters);
} catch (SQLException e) {
    logger.error("Failed to insert HAWB data: " + e.getMessage(), e);
    // Handle specific error conditions
    if (e.getMessage().contains("validation failed")) {
        // Handle validation errors
    } else if (e.getMessage().contains("STRUCT")) {
        // Handle STRUCT conversion errors
    }
}
```

### 3. Null Value Handling

```java
// The TypeHandler automatically handles null values
HawbData hawbData = null; // or incomplete data
parameters.put("hawbData", hawbData);

// The TypeHandler will set NULL for the STRUCT parameter
// No NullPointerException will be thrown
```

## Validation Requirements

### 1. Required Fields

The following fields are required for successful STRUCT conversion:

- `hawbNo` (String): HAWB number
- `hawbDate` (Date): HAWB date
- `origin` (String): Origin airport code
- `destination` (String): Destination airport code
- `pieces` (Integer): Number of pieces (> 0)
- `weight` (Double): Weight (> 0)

### 2. Field Length Limits

The utility class automatically truncates fields to prevent database errors:

- Shipper/Consignee names: 100 characters
- Addresses: 100 characters
- Cities: 50 characters
- Country codes: 3 characters
- Phone numbers: 20 characters
- Goods description: 500 characters
- Special handling: 100 characters

## Error Handling

### 1. Common Error Scenarios

| Error Type | Cause | Solution |
|------------|-------|----------|
| `NullPointerException` | Null HawbData object | Use validation before processing |
| `SQLException: validation failed` | Missing required fields | Populate all required fields |
| `SQLException: Failed to create STRUCT` | Database connection issues | Check PostgreSQL connection |
| `ClassCastException` | Wrong parameter type | Ensure parameter is HawbData instance |

### 2. Logging

The TypeHandler provides comprehensive logging at different levels:

- `FINE`: Successful operations and detailed flow
- `WARNING`: Data truncation and minor issues
- `SEVERE`: Errors and exceptions

Enable logging in your application:

```properties
cargo.cms.edi.ei.typehandler.level=FINE
cargo.cms.edi.ei.util.level=FINE
```

## PostgreSQL STRUCT Definition

Ensure your PostgreSQL database has the `t_hawb_ins` STRUCT type defined:

```sql
-- Example STRUCT type definition (adjust according to your database schema)
CREATE TYPE t_hawb_ins AS (
    hawb_no VARCHAR(20),
    hawb_date DATE,
    origin VARCHAR(3),
    destination VARCHAR(3),
    pieces INTEGER,
    weight NUMERIC(10,2),
    shipper_name VARCHAR(100),
    shipper_address1 VARCHAR(100),
    -- ... additional fields as per your requirements
);
```

## Performance Considerations

### 1. Connection Pooling

Use connection pooling to avoid overhead of creating STRUCT objects:

```xml
<dataSource type="POOLED">
    <property name="driver" value="org.postgresql.Driver"/>
    <property name="url" value="jdbc:postgresql://localhost:5432/yourdb"/>
    <property name="username" value="username"/>
    <property name="password" value="password"/>
    <property name="poolMaximumActiveConnections" value="20"/>
    <property name="poolMaximumIdleConnections" value="5"/>
</dataSource>
```

### 2. Batch Processing

For multiple HAWB records, consider batch processing:

```java
List<Map<String, Object>> batchParameters = new ArrayList<>();
// Add multiple parameter maps
sqlMapClient.executeBatch(new SqlMapClientCallback() {
    public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
        executor.startBatch();
        for (Map<String, Object> params : batchParameters) {
            executor.queryForObject("ediAimsFhlHAwbInsert", params);
        }
        return executor.executeBatch();
    }
});
```

## Testing

### 1. Unit Testing

Create unit tests to validate TypeHandler functionality:

```java
@Test
public void testHawbDataTypeHandler() {
    HawbDataTypeHandler handler = new HawbDataTypeHandler();
    HawbData hawbData = createValidHawbData();
    
    // Test parameter setting
    PreparedStatement ps = mock(PreparedStatement.class);
    handler.setParameter(ps, 1, hawbData, "STRUCT");
    
    // Verify no exceptions thrown
    verify(ps).setObject(eq(1), any(Struct.class));
}
```

### 2. Integration Testing

Test with actual PostgreSQL database:

```java
@Test
public void testProcedureCall() {
    Map<String, Object> parameters = createTestParameters();
    Object result = sqlMapClient.queryForObject("ediAimsFhlHAwbInsert", parameters);
    
    // Verify output parameters
    assertNull(parameters.get("errorMsg"));
    assertNotNull(parameters.get("docLineNo"));
}
```

## Troubleshooting

### 1. Debug Mode

Enable debug logging to trace STRUCT creation:

```java
Logger.getLogger("cargo.cms.edi.ei").setLevel(Level.FINE);
```

### 2. STRUCT Type Verification

Verify PostgreSQL STRUCT type definition:

```sql
SELECT 
    a.attname AS column_name,
    format_type(a.atttypid, a.atttypmod) AS data_type,
    a.attnum AS ordinal_position
FROM 
    pg_attribute a
    JOIN pg_type t ON a.attrelid = t.typrelid
WHERE 
    t.typname = 't_hawb_ins'
    AND a.attnum > 0
    AND NOT a.attisdropped
ORDER BY 
    a.attnum;
```

### 3. Common Issues

1. **Field Order Mismatch**: Ensure the order of fields in `convertHawbDataToStruct()` matches the PostgreSQL type definition exactly.

2. **Data Type Conversion**: Use `PostgreSQLStructUtil.convertToPostgreSQLType()` for proper type conversion.

3. **Null Handling**: The TypeHandler automatically handles null objects and null fields.

## Migration Guide

### 1. From Existing Implementation

1. Add the TypeHandler files to your project
2. Update your iBATIS configuration to include the TypeHandler registration
3. Modify existing parameterMaps to use the custom TypeHandler
4. Test thoroughly with your existing data

### 2. Backward Compatibility

The implementation maintains backward compatibility:

- Existing procedure calls continue to work
- No changes required to existing HawbData objects
- Output parameters remain unchanged

## Support

For issues or questions:

1. Check the logging output for detailed error information
2. Verify PostgreSQL STRUCT type definition
3. Ensure all required fields are populated
4. Review the parameter mapping configuration

This TypeHandler implementation provides a robust solution for PostgreSQL STRUCT mapping while maintaining compatibility with existing iBATIS configurations.