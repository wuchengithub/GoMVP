# GoMVP
MVP for Android


## Release

1.0.1

## How to Use

### Add app gradle
    dependencies {
        implementation 'com.wookii.com:gomvp:1.0.1'
    }

### Code

#### Presenter

    public class MainPresenter<T> extends BasePresenter<T>{

            @Override
            public T getValue() {
                return value;
            }

            @Override
            protected GoDataSource onModel() {
                //use default cach config.
                Cacher cach = new DefaultCacher(context)；
                return new GoRepository.Builder<T>()
                                        .cache(cach)
                                        .build();
            }

            public static abstract class PresenterAdapter implements GoPresenter.PresenterAdapter{
                    private BasePresenter presenter;
                    /**
                     * custom you http response sucess code
                     */
                    @Override
                    public Pair<String, String> onSuccessCode() {
                        return new Pair("code","1000");
                    }
             }
    }

#### Fragment

    public class MyFragment extends Fragment implements BaseView<MainPresenter<DemoBean>> {

    }

