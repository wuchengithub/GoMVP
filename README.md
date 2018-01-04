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
                return new GoRepository.Builder<T>().cache(cach).build();
            }

            public static abstract class PresenterAdapter implements GoPresenter.PresenterAdapter{
                private BasePresenter presenter;
                /**
                 * custom your http response sucess code, if you need.
                */
                @Override
                public Pair<String, String> onSuccessCode() {
                    return new Pair("code","1000");
                }
             }
    }

#### Fragment

    public class MyFragment extends Fragment implements BaseView<MainPresenter<DemoBean>> {
        public LoginFragment() {
            presenter = new MainPresenter(this);
            GoMVP mvp = new GoMVP.Builder().presenter(presenter).view(this).build();
        }

        @Override
        public void receiverData(MainPresenter<LoginInfo> t) {

        }

        @Override
        public void showDataError(MainPresenter<LoginInfo> t) {

        }
    }

