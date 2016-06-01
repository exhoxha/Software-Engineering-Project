package GasStation;

//interface
import java.sql.SQLException;

public interface IRecord
{
    public void Save(StationDB database) throws SQLException;
}
