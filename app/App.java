import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences.init(this);
        SignalManager.init(this);
    }
}
