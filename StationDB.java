package GasStation;
//connection with db
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StationDB
{
    public Connection connection;

    public void Connect() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/stationDB","root","");
    }

    public void Close() throws SQLException
    {
        connection.close();
    }

    public ArrayList<Stock> LoadStocks() throws SQLException
    {
        ArrayList<Stock> stockList = new ArrayList<Stock>();

        String query = "SELECT ID, Person, GasType, Quantity, PricePerLiter, Date from stock";
        PreparedStatement selectStatement = connection.prepareStatement(query);

        ResultSet results = selectStatement.executeQuery();

        while(results.next())
        {
            Stock stock = new Stock();

            stock.ID = results.getInt("ID");
            stock.GasType = results.getInt("GasType");
            stock.Quantity = results.getDouble("Quantity");
            stock.PricePerLiter = results.getDouble("PricePerLiter");
            stock.Date = results.getDate("Date");
            stock.Person = results.getString("Person");

            stockList.add(stock);
        }

        return stockList;
    }

    public ArrayList<Sale> LoadSales() throws SQLException
    {
        ArrayList<Sale> shitjet = new ArrayList<Sale>();

        String query = "SELECT ID, GasType, Quantity, PricePerLiter, Date from sale";
        PreparedStatement selectStatement = connection.prepareStatement(query);

        ResultSet results = selectStatement.executeQuery();

        while(results.next())
        {
            Sale sale = new Sale();

            sale.ID = results.getInt("ID");
            sale.GasType = results.getInt("GasType");
            sale.Quantity = results.getDouble("Quantity");
            sale.PricePerLiter = results.getDouble("PricePerLiter");
            sale.Date = results.getDate("Date");

            shitjet.add(sale);
        }

        return shitjet;
    }
}
