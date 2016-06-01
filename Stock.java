package GasStation;
//stock
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Stock extends Transaction
{
    public String Person;

    @Override
    public void Save(StationDB database) throws SQLException
    {
        String insertQuery="insert into stock "+
                                "(Person, GasType, Quantity, PricePerLiter, Date) "+
                            "values"+
                                "(?,?,?,?,?)";
        PreparedStatement insertStatement = database.connection.prepareStatement(insertQuery);
        insertStatement.setString(1, Person);
        insertStatement.setInt(2, this.GasType);
        insertStatement.setDouble(3, this.Quantity);
        insertStatement.setDouble(4, this.PricePerLiter);
        insertStatement.setDate(5, new Date(this.Date.getTime()));
        insertStatement.executeUpdate(); // execute insert statement

    }

    @Override
    public String toString()
    {
        return  super.toString()+", Person:" +Person;
    }
}
