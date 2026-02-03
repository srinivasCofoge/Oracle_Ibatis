package cargo.cms.edi.ei.typehandler;

import cargo.cms.edi.ei.bo.HawbData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for HawbDataTypeHandler.
 * Demonstrates the TypeHandler functionality and validates proper STRUCT conversion.
 * 
 * Note: These tests require JUnit and Mockito dependencies.
 * In a real project, add these to your build configuration:
 * 
 * Maven:
 * <dependency>
 *     <groupId>junit</groupId>
 *     <artifactId>junit</artifactId>
 *     <version>4.13.2</version>
 *     <scope>test</scope>
 * </dependency>
 * <dependency>
 *     <groupId>org.mockito</groupId>
 *     <artifactId>mockito-core</artifactId>
 *     <version>3.12.4</version>
 *     <scope>test</scope>
 * </dependency>
 */
public class HawbDataTypeHandlerTest {
    
    @Mock
    private PreparedStatement preparedStatement;
    
    @Mock
    private Connection connection;
    
    @Mock
    private Struct struct;
    
    private HawbDataTypeHandler typeHandler;
    
    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        typeHandler = new HawbDataTypeHandler();
        
        // Mock connection behavior
        when(preparedStatement.getConnection()).thenReturn(connection);
        when(connection.createStruct(anyString(), any(Object[].class))).thenReturn(struct);
    }
    
    @Test
    public void testSetParameterWithValidHawbData() throws SQLException {
        // Given
        HawbData hawbData = createValidHawbData();
        
        // When
        typeHandler.setParameter(preparedStatement, 1, hawbData, "STRUCT");
        
        // Then
        verify(preparedStatement).setObject(1, struct);
        verify(connection).createStruct(eq("t_hawb_ins"), any(Object[].class));
    }
    
    @Test
    public void testSetParameterWithNullHawbData() throws SQLException {
        // When
        typeHandler.setParameter(preparedStatement, 1, null, "STRUCT");
        
        // Then
        verify(preparedStatement).setNull(1, Types.STRUCT, "t_hawb_ins");
        verify(connection, never()).createStruct(anyString(), any(Object[].class));
    }
    
    @Test(expected = SQLException.class)
    public void testSetParameterWithInvalidType() throws SQLException {
        // Given
        String invalidParameter = "not a HawbData object";
        
        // When
        typeHandler.setParameter(preparedStatement, 1, invalidParameter, "STRUCT");
        
        // Then - SQLException should be thrown
    }
    
    @Test
    public void testSetParameterWithMinimalHawbData() throws SQLException {
        // Given
        HawbData hawbData = createMinimalHawbData();
        
        // When
        typeHandler.setParameter(preparedStatement, 1, hawbData, "STRUCT");
        
        // Then
        verify(preparedStatement).setObject(1, struct);
        verify(connection).createStruct(eq("t_hawb_ins"), any(Object[].class));
    }
    
    @Test
    public void testHandlesSQLExceptionDuringStructCreation() throws SQLException {
        // Given
        HawbData hawbData = createValidHawbData();
        when(connection.createStruct(anyString(), any(Object[].class)))
            .thenThrow(new SQLException("Mock SQL Exception"));
        
        // When & Then
        try {
            typeHandler.setParameter(preparedStatement, 1, hawbData, "STRUCT");
        } catch (SQLException e) {
            // Expected behavior - should propagate SQLException
            assert e.getMessage().contains("Failed to create PostgreSQL STRUCT");
        }
    }
    
    /**
     * Creates a valid HawbData object for testing.
     */
    private HawbData createValidHawbData() {
        HawbData hawbData = new HawbData();
        hawbData.setHawbNo("TEST12345");
        hawbData.setHawbDate(new Date());
        hawbData.setOrigin("HKG");
        hawbData.setDestination("LAX");
        hawbData.setPieces(10);
        hawbData.setWeight(100.5);
        hawbData.setShipperName("Test Shipper Inc");
        hawbData.setShipperAddress1("123 Test Street");
        hawbData.setShipperCity("Hong Kong");
        hawbData.setShipperCountry("HKG");
        hawbData.setConsigneeName("Test Consignee Corp");
        hawbData.setConsigneeAddress1("456 Test Avenue");
        hawbData.setConsigneeCity("Los Angeles");
        hawbData.setConsigneeCountry("USA");
        hawbData.setGoodsDescription("Test Goods");
        hawbData.setSpecialHandling("GEN");
        return hawbData;
    }
    
    /**
     * Creates a minimal HawbData object with only required fields.
     */
    private HawbData createMinimalHawbData() {
        HawbData hawbData = new HawbData();
        hawbData.setHawbNo("MIN12345");
        hawbData.setHawbDate(new Date());
        hawbData.setOrigin("HKG");
        hawbData.setDestination("LAX");
        hawbData.setPieces(1);
        hawbData.setWeight(1.0);
        return hawbData;
    }
}