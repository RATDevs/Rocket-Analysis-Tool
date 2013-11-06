package rat.controller.dataIO;

import java.io.IOException;

public interface IDataOutputAdapter {

	public void writeData(Object Data, String path) throws IOException;

}
