package mine.seandragon.other.search;

import java.io.IOException;
import java.util.concurrent.Callable;

public interface ISearch extends Callable{
    long search(String question) throws IOException;
}
