package com.example.kjy.rxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    private String TAG = "subscribe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // textView = (TextView) findViewById(R.id.textView);
        Observable<String> observable = Observable.create(subscriber -> {
            Log.d(TAG, "call");
            subscriber.onNext("Hello");
            subscriber.onNext("Android");
            subscriber.onCompleted();
        });


        Observable<String> observable1 = Observable.just("Hello!");
        observable1.subscribe(s -> {
            Log.d(TAG, s + ">>");

        });

        observable.subscribe(s -> {
            Log.d(TAG, s);
            ((TextView) findViewById(R.id.textView)).setText(s);
        });

        Observable.from(new String[]{"grape", "banana", "apple", "peach", "watermelon"}).toSortedList().subscribe(string -> {
            for (String s : string) Log.i("SORT", s);
        });

        Observable.just("hi").subscribe(string -> Log.i(string, string));


        asyncSubject();
        behaviorSubject();
        publishSubject();
        replaySubject();
        interval();
//
//        time.subscribe(count -> {
//            //Log.i("count1", count + "");
//
//            for (DDD count1 : ddd) {
//                if (count1.getStart() <= (long) count && count1.getEnd() >= (long) count) {
//                    Log.i("count", count1.getName());
//                }
//            }
//
//        });
//
//        time.subscribe(count -> {
//            for (DDD count1 : ddd2) {
//                if (count1.getStart() <= (long) count && count1.getEnd() >= (long) count) {
//                    Log.i("count", count1.getName() + " " + count1.getAddress());
//                }
//            }
//        });


//         PublishSubject<Object> publishSubject = PublishSubject.create();
//        //publishSubject.onNext("1");
//        publishSubject.subscribe(count -> {
//            //Log.i("countgg", count);
//            for (DDD count1 : ddd2) {
//                if (count1.getStart() <= (long) count && count1.getEnd() >= (long) count) {
//                    Log.i("count", count1.getName() + " " + count1.getAddress());
//                }
//            }
//        });
//        publishSubject.onNext(time);

//        observable.subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                textView.setText(s);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//
//            }
//        }, new Action0() {
//            @Override
//            public void call() {
//
//            }
//        });
//        observable.subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "onNext");
//                ((TextView) findViewById(R.id.textView)).setText(s);
//            }
//        });


//        observable.map(new Func1<String, Integer>() {
//            @Override
//            public Integer call(String i) {
//                return i.length();
//            }
//        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void asyncSubject() {
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.onNext("a");
        subject.onNext("b");
        subject.subscribe(string -> Log.i("asyncSubject1", string));
        subject.onNext("c");
        subject.subscribe(string -> Log.i("asyncSubject2", string));
        subject.onNext("d");
        subject.onCompleted();
    }

    private void behaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.onNext("a");
        subject.onNext("b");
        subject.subscribe(string -> Log.i("behaviorSubject1", string));
        subject.onNext("c");
        subject.subscribe(string -> Log.i("behaviorSubject2", string));
        subject.onNext("d");
        subject.onCompleted();
    }

    private void publishSubject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("a");
        subject.onNext("b");
        subject.subscribe(string -> Log.i("publishSubject1", string));
        subject.onNext("c");
        subject.subscribe(string -> Log.i("publishSubject2", string));
        subject.onNext("d");
        subject.onCompleted();
    }

    private void replaySubject() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.onNext("a");
        subject.onNext("b");
        subject.subscribe(string -> Log.i("replaySubject1", string));
        subject.onNext("c");
        subject.subscribe(string -> Log.i("replaySubject2", string));
        subject.onNext("d");
        subject.onCompleted();
    }

    private void interval() {
        List<DDD> ddd = new ArrayList<>();
        ddd.add(new DDD("aaa", 1, 9, "222"));
        ddd.add(new DDD("aaaaa", 12, 20, "111"));
        ddd.add(new DDD("bbaaa", 21, 25, "333"));

        List<DDD> ddd2 = new ArrayList<>();
        ddd2.add(new DDD("AAA", 1, 3, "DDD"));
        ddd2.add(new DDD("BBB", 5, 10, "EEE"));
        ddd2.add(new DDD("CCC", 13, 25, "FFF"));

        Observable time = Observable.interval(5, TimeUnit.SECONDS, Schedulers.computation());
        PublishSubject<Long> publishSubject = PublishSubject.create();

        publishSubject.subscribe(count -> {
            Log.i("Acount1", count + "");
            for (DDD count1 : ddd2) {
                if (count1.getStart() <= count && count1.getEnd() >= count) {
                    Log.i("Acount1", count1.getName() + " " + count1.getAddress());
                }
            }
        });

        time.subscribe(count -> {
            Log.i("2count", "dd");
            publishSubject.onNext((long) count);

        });
    }

    public class DDD {
        String name;
        int start;
        int end;
        String address;

        DDD(String name, int start, int end, String address) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
