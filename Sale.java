package GasStation;
//sale operations
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Sale extends Transaction
{
    @Override
    public void Save(StationDB database) throws SQLException
    {
        String insertQuery="insert into sale "+
                                "(GasType, Quantity, PricePerLiter, Date) "+
                            "values"+
                                "(?,?,?,?)";

        PreparedStatement insertStatement = database.connection.prepareStatement(insertQuery);
        insertStatement.setInt(1, this.GasType);
        insertStatement.setDouble(2, this.Quantity);
        insertStatement.setDouble(3, this.PricePerLiter);
        insertStatement.setDate(4, new Date(this.Date.getTime()));
        insertStatement.executeUpdate(); // execute insert statement
    }
}
