# Oracle_Ibatis

Oracle iBATIS files with Custom TypeHandler for PostgreSQL STRUCT Mapping

## Custom TypeHandler Implementation for PostgreSQL STRUCT Mapping

This repository now includes a complete custom TypeHandler implementation to resolve `NullPointerException` when mapping Java `HawbData` objects to PostgreSQL `t_hawb_ins` STRUCT type in iBATIS.

### Features

✅ **Custom TypeHandler** - Complete implementation for HawbData to PostgreSQL STRUCT conversion  
✅ **Null Value Handling** - Proper handling of null objects and fields  
✅ **Error Management** - Comprehensive error handling with meaningful messages  
✅ **Field Mapping** - Support for all 100+ fields from HawbData to t_hawb_ins structure  
✅ **Validation** - Built-in validation for required fields  
✅ **Utilities** - Helper classes for data conversion and PostgreSQL operations  
✅ **Documentation** - Complete usage guide and configuration examples  
✅ **Testing** - Unit tests and examples for validation  

### Quick Start

1. **Add the TypeHandler to your iBATIS configuration:**
   ```xml
   <typeHandler javaType="cargo.cms.edi.ei.bo.HawbData" 
                jdbcType="STRUCT" 
                callback="cargo.cms.edi.ei.typehandler.HawbDataTypeHandler"/>
   ```

2. **Use the custom parameterMap:**
   ```xml
   <parameterMap id="ediAimsFhlHAwbInsertParMap" class="map">
       <parameter property="hawbData" 
                  jdbcType="STRUCT" 
                  javaType="cargo.cms.edi.ei.bo.HawbData" 
                  mode="IN"
                  typeHandler="cargo.cms.edi.ei.typehandler.HawbDataTypeHandler"/>
       <!-- other parameters -->
   </parameterMap>
   ```

3. **Call the PostgreSQL procedure:**
   ```xml
   <procedure id="ediAimsFhlHAwbInsert" parameterMap="ediAimsFhlHAwbInsertParMap">
       {call wosint.PKG_FHL__FUN_INS_HAWB(?,?,?,?,?,?,?,?)}
   </procedure>
   ```

### Components

- **`HawbDataTypeHandler.java`** - Main TypeHandler implementation
- **`HawbData.java`** - Business object for House Air Waybill data
- **`PostgreSQLStructUtil.java`** - Utility class for PostgreSQL operations
- **`sqlmaphawbdata.xml`** - iBATIS configuration with TypeHandler
- **`SqlMapConfig-sample.xml`** - Sample configuration file
- **`HAWB_TYPEHANDLER_DOCUMENTATION.md`** - Complete documentation

### Documentation

See [HAWB_TYPEHANDLER_DOCUMENTATION.md](HAWB_TYPEHANDLER_DOCUMENTATION.md) for complete usage guide, configuration examples, and troubleshooting.

### Original Files

The repository contains original Oracle iBATIS SQL map files:
- `sqlqueryflight.xml`
- `sqlqueryimport.xml`
- `sqlqueryimport_part1.xml` through `sqlqueryimport_part4.xml`
