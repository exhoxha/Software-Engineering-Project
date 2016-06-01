package GasStation;
//stacion
import java.sql.SQLException;
import java.util.List;

public class Station
{
    //Gas Type Constants
    public static final int Diesel = 0;
    public static final int Petrol = 1;
    public static final int Gas = 2;

    public GasTank[] gasTanks;
    public List<Stock> stockList;
    public List<Sale> saleList;
    public StationDB database;

    public Station()
    {
        //Array with three tanks
        gasTanks = new GasTank[3];
        gasTanks[0] = new GasTank(Station.Diesel,"Diesel",0,1000);
        gasTanks[1] = new GasTank(Station.Petrol,"Petrol",0,1000);
        gasTanks[2] = new GasTank(Station.Gas,"Gas",0,1000);


        //DB
        database = new StationDB();
    }

    public void LoadDataFromDB() throws SQLException, ClassNotFoundException
    {
        Result result = new Result();

        database.Connect();

        //Get information from DB
        for(int i=0; i<3; i++)
        {
            gasTanks[i].Load(database);
        }

        //Load stock and sales
        stockList = database.LoadStocks();
        saleList = database.LoadSales();

        database.Close();

        result.Success = true;
    }

    public Result PutGasInTank(Stock stock)
    {
        Result result = new Result();

        try
        {
            if(stock.GasType<0 || stock.GasType>2)
            {
                result.Success = false;
                result.Reason = "Gas type can only be 0, 1 or 2 (0 diesel, 1 petrol, 2 gas)";
            }
            else
            {
                //find the right tank and put the gas in
                GasTank tank = gasTanks[stock.GasType];
                result = tank.PutIn(stock.Quantity);

                //Did the gas go in the tank
                if(result.Success)
                {
                    //Save the stock
                    database.Connect();

                    stock.Save(database);
                    tank.Save(database);

                    database.Close();

                    //add the stock on the loaded list
                    stockList.add(stock);
                }
            }
        }
        catch (Exception ex)
        {
            result.Success=false;
            result.Reason = ex.getMessage();
        }

        return result;
    }

    public Result SellGas(Sale sale)
    {
        Result result = new Result();

        try
        {
            if(sale.GasType<0 || sale.GasType>2)
            {
                result.Success = false;
                result.Reason = "Gas type can only be 0, 1 or 2 (0 diesel, 1 petrol, 2 gas)";
            }
            else
            {
                //Get the right type of gas
                GasTank tank = gasTanks[sale.GasType];
                result = tank.TakeOut(sale.Quantity);

                //Did the tank have gas
                if(result.Success)
                {
                    //save
                    database.Connect();

                    sale.Save(database);
                    tank.Save(database);

                    database.Close();

                    //add the sale on the loaded list
                    saleList.add(sale);
                }
            }
        }
        catch (Exception ex)
        {
            result.Success=false;
            result.Reason = ex.getMessage();
        }

        return result;
    }

    public double GetTotalQuantityOfGasStock(int gasType)
    {
        double quantity = 0;

        for(Stock stock : stockList)
        {
            if (stock.GasType==gasType)
            {
                quantity = quantity + stock.Quantity;
            }
        }

        return quantity;
    }

    public double GetTotalQuantityOfGasSales(int gasType)
    {
        double quantity = 0;

        for(Sale sale : saleList)
        {
            if (sale.GasType==gasType)
            {
                quantity = quantity + sale.Quantity;
            }
        }

        return quantity;
    }

    public double GetAmountOfMoneyFromSales(int gasType)
    {
        double amountOfMoney = 0;

        for(Sale sale : saleList)
        {
            if (sale.GasType==gasType)
            {
                amountOfMoney = amountOfMoney + sale.Cost();
            }
        }

        return amountOfMoney;
    }

    public double GetAmountOfMoneyFromStock(int gasType)
    {
        double amountOfMoney = 0;

        for(Stock stock : stockList)
        {
            if (stock.GasType==gasType)
            {
                amountOfMoney = amountOfMoney + stock.Cost();
            }
        }

        return amountOfMoney;
    }

}
