package GasStation;
//user

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GasTank implements IRecord
{
    private int GasType;
    private double ActualQuantity;
    private double MaximumQuantity;
    private String GasName;

    public GasTank(int gasType, String gasName, double actualQuantity, double maximumQuantity)
    {
        this.GasType = gasType;
        this.ActualQuantity = actualQuantity;
        this.MaximumQuantity = maximumQuantity;
        this.GasName = gasName;
    }

    public double getActualQuantity()
    {
        return ActualQuantity;
    }

    public Result PutIn(double quantity)
    {
        Result result = new Result();

        //Check if the tank will be overloaded with gas
        if(this.ActualQuantity+quantity<=this.MaximumQuantity)
        {
            result.Success = true;
            this.ActualQuantity = this.ActualQuantity+quantity;
        }
        else
        {
            result.Success = false;
            result.Reason = "You can not put any more gas in the tank as it is full!";
        }

        return result;
    }

    public Result TakeOut(double quantity)
    {
        Result result  = new Result();

        //Is there any gas to sell
        if(quantity<=this.ActualQuantity)
        {
            result.Success = true;
            this.ActualQuantity = this.ActualQuantity - quantity;
        }
        else
        {
            result.Success = false;
            result.Reason = "The tank does not have anymore fuel!";
        }

        return result;
    }

    @Override
    public void Save(StationDB database) throws SQLException
    {
         String insertQuery="update gastank "+
                            "set ActualQuantity=?"+
                            "where GasType=?";
        PreparedStatement insertStatement = database.connection.prepareStatement(insertQuery);
        insertStatement.setDouble(1,this.ActualQuantity);
        insertStatement.setInt(2, this.GasType);
        insertStatement.executeUpdate(); // execute insert statement
    }

    public void Load(StationDB database) throws SQLException
    {
        String query = "SELECT GasName, ActualQuantity, MaximumQuantity from gastank WHERE GasType = ?";
        PreparedStatement selectStatement = database.connection.prepareStatement(query);
        selectStatement.setInt(1, GasType);

        ResultSet results = selectStatement.executeQuery();

        if (results.first())
        {
            this.GasName = results.getString("GasName");
            this.ActualQuantity = results.getInt("ActualQuantity");
            this.MaximumQuantity = results.getDouble("MaximumQuantity");
        }
    }
}
