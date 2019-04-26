package utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fen
 */
public class SWFetcher {

    class FetchSWAPI implements Callable<String> {

        int id;

        FetchSWAPI(int id) {
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            return getSwappiData(id);
        }
    }
    
    public List<String> getSeveralSwappiData() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            FetchSWAPI fs = new FetchSWAPI(i);
            Future future = executor.submit(fs);
            futures.add(future);
        }
        List<String> results = new ArrayList();
        for(Future<String> f: futures) {
            String status =  f.get(5, TimeUnit.SECONDS);
            results.add(status);
        }
        executor.shutdown();
        return results;
    }

    public String getSwappiData(int id) throws MalformedURLException, IOException {
        URL url = new URL("https://swapi.co/api/people/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server");
        Gson gson = new Gson();
        Scanner scan = new Scanner(con.getInputStream());
        //StringBuilder sb = new StringBuilder();
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        System.out.println(jsonStr);
        scan.close();
        return jsonStr;
    }

}
