package GasStation;
//veprimet
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Transaction implements IRecord
{
    public int ID;
    public int GasType;
    public double Quantity;
    public double PricePerLiter;
    public Date Date;

    public double Cost()
    {
        return Quantity * PricePerLiter;
    }

    @Override
    public String toString()
    {
        //UI string for a transaction sale or stock
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        return  "Transaction date: "+sdf.format(Date)+
                ", Gas Type: " + GasType +
                ", Quantity: "+Quantity +
                ", Price: "+PricePerLiter+
                ", Cost: "+Cost();
    }
}
