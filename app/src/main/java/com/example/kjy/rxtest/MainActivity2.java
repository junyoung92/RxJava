package com.example.kjy.rxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observables.ConnectableObservable;
import rx.observables.MathObservable;
import rx.subjects.PublishSubject;

public class MainActivity2 extends AppCompatActivity {

    String fruits[] = new String[]{"grape", "banana", "apple", "peach", "watermelon", "strawberry", "orange"};
    String colors[] = new String[]{"black", "red", "yellow", "pink"};
    Integer numbers[] = new Integer[]{4, 1, 2, 4, 4, 5, 6, 7, 7, 2, 9, 10, 2};

    Observable<String> observable = Observable.create(subscriber -> {
        Log.i("observable", "create");
        subscriber.onNext("HI");
    });

    Observable emptyObservable = Observable.empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //  CreatingObservables();
        // TransformingObservables();
        FilteringObservables();
//        CombiningObservables();
//        ErrorHandlingOperators();
//        ObservableUtilityOperators();
//        ConditionalAndBooleanOperators();
//        MathematicalAndAggregateOperators();
//        ConnectableObservableOperators();
    }

    private void CreatingObservables() {
        createObservable();
        deferObservable();
        emptyNeverThrowObservable();
        fromObservable();
        intervalObservable();
        justObservable();
        rangeObservable();
        repeatObservable();
        timerObservable();
    }

    private void TransformingObservables() {
        bufferObservable();
        flatMapObservable();
        concatMapObservable();
        groupByObservable();
        mapObservable();
        scanObservable();
        windowObservable();
    }

    private void FilteringObservables() {
        debounceObservable();
        distinctObservable();
        elementAtObservable();
        filterObservable();
        firstObservable();
        lastObservable();
        ignoreElementsObservable();
        sampleObservable();
        skipObservable();
        skipLastObservable();
        takeObservable();
        takeLastObservable();
    }

    private void CombiningObservables() {
        combineLatestObservable();
        joinObservable();
        mergeObservable();
        startWithObservable();
        switchObservable();
        zipObservable();
    }

    private void ErrorHandlingOperators() {
        catchOperator();
        retryOperator();
    }

    private void ObservableUtilityOperators() {
        delayOperator();
        timeoutOperator();
    }

    private void ConditionalAndBooleanOperators() {
        allOperator();
        ambOperator();
        containsOperator();
        defaultEmptyOperator();
        sequenceEqualOperator();
        skipUntilOperator();
        skipWhileOperator();
        takeUntilOperator();
        takeWhileOperator();
    }

    private void MathematicalAndAggregateOperators() {
        averageOperator();
        concatOperator();
        countOperator();
        maxOperator();
        minOperator();
        reduceOperator();
        sumOperator();
    }

    private void ConnectableObservableOperators() {
        connectOperator();
        publishOperator();
        refCountOperator();
        replayOperator();
    }

    /* CreatingObservables */
    private void createObservable() {
        Observable.create(subscriber -> {
            Log.i("createObservable", "create");
            for (int i = 0; i < 5; i++) {
                subscriber.onNext(i);
            }
            subscriber.onCompleted();
        }).subscribe(i -> Log.i("createObservable", i + ""), throwable -> Log.i("createObservable", "error"), () -> Log.i("createObservable", "completed"));
    }

    private void deferObservable() {
        Observable.defer(() -> {
            Log.i("deferObservable", "defer");
            return observable;
        }).subscribe(s -> Log.i("deferObservable", s));
    }

    private void emptyNeverThrowObservable() {
        Observable.empty().subscribe(o -> Log.i("emptyObservable", "onNext"), throwable -> Log.i("emptyObservable", "onError"), () -> Log.i("emptyObservable", "onCompleted"));  //onCompleted
        Observable.never().subscribe(o -> Log.i("neverObservable", "onNext"), throwable -> Log.i("neverObservable", "onError"), () -> Log.i("neverObservable", "onCompleted"));
        Observable.error(new Throwable()).subscribe(o -> Log.i("throwObservable", "onNext"), throwable -> Log.i("throwObservable", "onError"), () -> Log.i("throwObservable", "onCompleted"));  //onError
    }

    private void fromObservable() {
        Observable.from(fruits).subscribe(string -> Log.i("fromObservable", string));
    }

    private void intervalObservable() {
        PublishSubject<Long> publishSubject = PublishSubject.create();
        publishSubject.subscribe(time -> Log.i("intervalObservable1", time + ""));
        Observable.interval(1, TimeUnit.SECONDS).take(5).subscribe(time -> publishSubject.onNext(time));
        Observable.interval(2, TimeUnit.SECONDS).take(5).subscribe(time -> Log.i("intervalObservable2", time + ""));
    }

    private void justObservable() {
        Observable.just(fruits).subscribe(string -> {
            for (String s : string)
                Log.i("justObservable", s);
        });
    }

    private void rangeObservable() {
        Observable.range(1, 5).subscribe(count -> Log.i("rangeObservable", count + ""));
    }

    private void repeatObservable() {
        Observable<String> repeatObservable = Observable.just("repeatObservable");
        repeatObservable.repeat(5).subscribe(string -> Log.i("repeatObservable1", string));

        Observable.from(colors).repeatWhen(observable -> observable).take(10).subscribe(s -> Log.i("repeatObservable2", s));
    }

    private void timerObservable() {
        Observable.timer(3, TimeUnit.SECONDS).repeat(3).subscribe(num -> Log.i("timerObservable", "timerObservable")); //Observable.interval(3, TimeUnit.SECONDS).subscribe(num -> Log.i("timerObservable", "intervalObservable"));
    }

    /* TransformingObservables */
    private void bufferObservable() {
        Observable.create(subscriber -> {
            for (int i = 0; i < 20; i++) {
                subscriber.onNext(i);
            }
        }).buffer(8).subscribe(list -> {
            Log.i("bufferObservable", list.toString());
        });

        Observable.create(subscriber -> {
            for (int i = 0; i < 3; i++) {
                for (String s : fruits)
                    subscriber.onNext(s);
            }
        }).buffer(4).subscribe(list -> {
            Log.i("bufferObservable2", list.toString());
        });
    }

    private void flatMapObservable() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.flatMap(string -> Observable.create(subscriber -> {
            subscriber.onNext(string);
        })).take(20).subscribe(object -> Log.i("flatMapObservable", object.toString()), throwable -> Log.i("flatMapThrowable", throwable.toString()));

        Observable.interval(3, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time1  " + time + ""));
        Observable.interval(5, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time2  " + time + ""));
    }

    private void concatMapObservable() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.concatMap(string -> Observable.create(subscriber -> {
            subscriber.onNext(string);
        })).retry().take(20).subscribe(object -> Log.i("concatMapObservable", object.toString()), throwable -> Log.i("concatMapThrowable", throwable.toString()));

        Observable.interval(3, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time1  " + time + ""));
        Observable.interval(5, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time2  " + time + ""));

    }

    private void groupByObservable() {
        Observable.range(1, 10).groupBy(n -> n % 3).subscribe(groupedObservable -> groupedObservable.toList().subscribe(items -> Log.i("groupByObservable", items.toString())));
    }

    private void mapObservable() {
        Observable.from(colors).map(string -> string.length()).subscribe(num -> Log.i("mapObservable1", num + ""));
        Observable.from(colors).map(string -> string + " " + string.toUpperCase()).subscribe(string -> Log.i("mapObservable2", string));
    }

    private void scanObservable() {
        Observable.from(colors).repeat(2).scan((str1, str2) -> str1 + " " + str2).subscribe(string -> Log.i("scanObservable1", string));
        Observable.from(colors).scan("Color :", (str1, str2) -> str1 + " " + str2).subscribe(string -> Log.i("scanObservable2", string));
    }

    private void windowObservable() {
        Observable.create(subscriber -> {
            for (int i = 0; i < 3; i++) {
                for (String s : fruits)
                    subscriber.onNext(s);
            }
        }).window(4).subscribe(objectObservable -> {
            objectObservable.toList().subscribe(list -> {
                Log.i("windowObservable1", list.toString());
            });
        });
        Observable.interval(1, TimeUnit.SECONDS).window(5, 10, TimeUnit.SECONDS).take(30, TimeUnit.SECONDS).subscribe(longObservable -> longObservable.toList().subscribe(longs -> Log.i("windowObservable2", longs.toString())));
    }

    /* FilteringObservables */
    private void debounceObservable() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.debounce(2, TimeUnit.SECONDS).take(10).subscribe(string -> Log.i("debounceObservable", string));

        Log.i("debounceObservable", "start");
        Observable.interval(5, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time1  " + time + ""));
        Observable.interval(6, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time2  " + time + ""));
    }

    private void distinctObservable() {
        Observable.from(numbers).distinct().subscribe(num -> Log.i("distinctObservable1", num + ""));
        Observable.from(fruits).distinct(String::length).subscribe(integer -> Log.i("distinctObservable2", integer + ""));
        Observable.from(numbers).distinctUntilChanged().subscribe(num -> Log.i("distinctUntilChanged", num + ""));
    }

    private void elementAtObservable() {
        Observable.from(fruits).elementAt(2).subscribe(fruit -> Log.i("elementAtObservable1", fruit));
        Observable.from(fruits).elementAt(12).subscribe(fruit -> Log.i("elementAtObservable2", fruit), throwable -> Log.i("elementAtObservable", "onError : " + throwable.toString()));
        Observable.from(fruits).elementAtOrDefault(2, "default").subscribe(fruit -> Log.i("elementAtOrDefault1", fruit));
        Observable.from(fruits).elementAtOrDefault(12, "default").subscribe(fruit -> Log.i("elementAtOrDefault2", fruit));
    }

    private void filterObservable() {
        Observable.from(fruits).filter(fruit -> fruit.length() > 5).subscribe(string -> Log.i("filterObservable1", string));
        Observable.just(1, 2, 3, fruits, "A", "b").ofType(Integer.class).subscribe(o -> Log.i("filterObservable2", o.toString()));
        Observable.just(1, 2, 3, fruits, "a", "b").ofType(String.class).map(string -> string.toUpperCase() + "(" + string + ")").subscribe(o -> Log.i("filterObservable3", o.toString()));
    }

    private void firstObservable() {
        Observable.from(fruits).first().subscribe(first -> Log.i("firstObservable", first));
        Observable.empty().firstOrDefault("default").subscribe(first -> Log.i("firstObservable2", first.toString()));
    }

    private void lastObservable() {
        Observable.from(fruits).last().subscribe(last -> Log.i("lastObservable", last));
    }

    private void ignoreElementsObservable() {
        Observable.from(fruits).ignoreElements().subscribe(string -> Log.i("ignoreElementsObservable", string));
    }

    private void sampleObservable() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.sample(5, TimeUnit.SECONDS).take(10).subscribe(string -> Log.i("sampleObservable", string));

        Observable.interval(1, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time1  " + time + ""));
        Observable.interval(2, TimeUnit.SECONDS).subscribe(time -> publishSubject.onNext("time2  " + time + ""));
    }

    private void skipObservable() {
        Observable.from(fruits).skip(3).subscribe(fruit -> Log.i("skipObservable", fruit));
    }

    private void skipLastObservable() {
        Observable.from(fruits).skipLast(3).subscribe(fruit -> Log.i("skipLastObservable", fruit));
    }

    private void takeObservable() {
        Observable.from(fruits).take(3).subscribe(fruit -> Log.i("takeObservable", fruit));
    }

    private void takeLastObservable() {
        Observable.from(fruits).takeLast(3).subscribe(fruit -> Log.i("takeLastObservable", fruit));
    }

    /* CombiningObservables */
    private void combineLatestObservable() {
        Observable<Long> observable1 = Observable.interval(3, TimeUnit.SECONDS);
        Observable<Long> observable2 = Observable.interval(5, TimeUnit.SECONDS);

        Observable.combineLatest(observable1, observable2, (object1, object2) -> object1 + " " + object2).take(10).subscribe(o -> Log.i("combineLatestObservable", o.toString()));
    }

    private void joinObservable() {
        Log.i("joinObservable", "join");
        Observable<String> observable1 = Observable.interval(2, TimeUnit.SECONDS).map(time -> "observable1 : " + time);
        Observable<String> observable2 = Observable.interval(3, TimeUnit.SECONDS).map(time -> " observable2 : " + time);
        observable1.join(observable2, s1 -> Observable.timer(2, TimeUnit.SECONDS), s2 -> Observable.timer(0, TimeUnit.SECONDS), (s3, s4) -> s3 + s4).take(10).subscribe(string -> Log.i("joinObservable", string));

    }

    private void mergeObservable() {
        Observable.merge(Observable.from(fruits), Observable.from(colors)).subscribe(string -> Log.i("mergeObservable", string));
    }

    private void startWithObservable() {
        Observable.from(colors).startWith("==COLORS==").subscribe(color -> Log.i("startWithObservable", color));
        Observable.from(colors).startWith(Observable.just("a", "b", "c")).subscribe(string -> Log.i("startWithObservable2", string));
    }

    private void switchObservable() { //?
        Observable<Long> observable1 = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> observable2 = Observable.interval(5, TimeUnit.SECONDS).map(value -> value * 3);
        Observable.switchOnNext(Observable.just(observable1, observable2)).subscribe(time -> Log.i("switchObservable", time.toString()));

        Log.i("switchObservable2", "zz");
        Observable<Long> observable3 = Observable.interval(2, TimeUnit.SECONDS);
        Observable<String> observable4 = Observable.interval(3, TimeUnit.SECONDS).map(value -> value.toString()).delay(10, TimeUnit.SECONDS);

        observable3.switchOnNext(Observable.just(observable4)).subscribe(string -> Log.i("switchObservable2", string));

        Observable.just("HI").switchOnNext(Observable.just(observable3)).subscribe(o -> Log.i("switchObservable3", o.toString()));

    }

    private void zipObservable() {
        Observable<String> observable1 = Observable.from(fruits);
        Observable<String> observable2 = Observable.from(colors);
        Observable.zip(observable1, observable2, (string1, string2) -> "fruit : " + string1 + ", color : " + string2).subscribe(string -> Log.i("zipObservable1", string));

        Observable<Long> observable3 = Observable.interval(3, TimeUnit.SECONDS);
        Observable<Long> observable4 = Observable.interval(5, TimeUnit.SECONDS);
        Observable.zip(observable3, observable4, (time1, time2) -> "time1 : " + time1 + " time2 : " + time2).subscribe(time -> Log.i("zipObservable2", time));

        Observable<Long> observable5 = Observable.interval(2, TimeUnit.SECONDS);
        Observable.zip(observable3, observable1, observable5, (num, fruit, time) -> num + ". " + fruit + "(" + time + ")").subscribe(result -> Log.i("zipObservable3", result));
    }

    /* ErrorHandlingOperators */
    private void catchOperator() {
        Observable<String> observable1 = Observable.just("1").delay(1, TimeUnit.SECONDS);
        Observable<Long> observable2 = Observable.interval(5, TimeUnit.SECONDS);
        Observable<Long> observable3 = Observable.interval(3, TimeUnit.SECONDS);

        Observable.merge(observable1, observable2, observable3).timeout(2, TimeUnit.SECONDS).onErrorReturn(throwable -> "TIMEOUT").subscribe(o -> Log.i("catch-onErrorReturn", o.toString()), t -> Log.i("catch-onErrorReturn", t.toString()));
        Observable.merge(observable1, observable2, observable3).timeout(2, TimeUnit.SECONDS).onErrorResumeNext(throwable -> Observable.from(colors)).subscribe(o -> Log.i("catch-onErrorResumeNext", o.toString()), t -> Log.i("catch-onErrorResumeNext", t.toString()));

    }

    private void retryOperator() {
        Observable<String> observable1 = Observable.just("retry").delay(1, TimeUnit.SECONDS);
        Observable<Long> observable2 = Observable.interval(5, TimeUnit.SECONDS);
        Observable<Long> observable3 = Observable.interval(3, TimeUnit.SECONDS);
        Observable.merge(observable1, observable2, observable3).timeout(2, TimeUnit.SECONDS).retry().take(30).subscribe(o -> Log.i("retryOperator", o.toString()), t -> Log.i("retryOperator", t.toString()));
    }

    /* ObservableUtilityOperators */
    private void delayOperator() {
        Log.i("delayOperator", new SimpleDateFormat("hh:mm:ss").format(new Date()));
        Observable.from(fruits).delay(10, TimeUnit.SECONDS).subscribe(fruit -> Log.i("delayOperator", new SimpleDateFormat("hh:mm:ss").format(new Date()) + " " + fruit));
    }


    private void timeoutOperator() {
        Log.i("timeoutOperator", "START");
        Observable<String> observable1 = Observable.just("1").delay(1, TimeUnit.SECONDS);
        Observable<Long> observable2 = Observable.interval(5, TimeUnit.SECONDS);
        Observable<Long> observable3 = Observable.interval(3, TimeUnit.SECONDS);
        Observable.merge(observable1, observable2, observable3).timeout(2, TimeUnit.SECONDS).subscribe(o -> Log.i("timeoutOperator", o.toString()), t -> Log.i("timeoutOperator", t.toString()));
    }

    /* ConditionalAndBooleanOperators */
    private void allOperator() {
        Observable.from(numbers).all(number -> number < 5).subscribe(result -> Log.i("allOperator1", result + ""));
        Observable.from(numbers).all(number -> number < 20).subscribe(result -> Log.i("allOperator2", result + ""));
    }

    private void ambOperator() {
        Observable<String> observable1 = Observable.from(fruits).delay(4, TimeUnit.SECONDS);
        Observable<String> observable2 = Observable.from(colors).delay(2, TimeUnit.SECONDS);
        Observable<Integer> observable3 = Observable.from(numbers).delay(3, TimeUnit.SECONDS);

        Observable.amb(observable1, observable2, observable3).subscribe(result -> Log.i("ambOperator", result.toString()));
    }

    private void containsOperator() {
        Observable<String> observable = Observable.from(fruits);

        observable.contains("orange").subscribe(contain -> Log.i("containsOperator1", contain + ""));
        observable.contains("lemon").subscribe(contain -> Log.i("containsOperator2", contain + ""));

        observable.exists(s -> s.equals("orange")).subscribe(exist -> Log.i("containsOperator_exists1", exist + ""));
        observable.exists(s -> s.equals("lemon")).subscribe(exist -> Log.i("containsOperator_exists2", exist + ""));

        emptyObservable.isEmpty().subscribe(empty -> Log.i("containsOperator_isEmpty1", empty + ""));
        observable.isEmpty().subscribe(empty -> Log.i("containsOperator_isEmpty2", empty + ""));
    }

    private void defaultEmptyOperator() {
        emptyObservable.defaultIfEmpty("Empty").subscribe(result -> Log.i("defaultEmptyOperator", result.toString()));
    }

    private void sequenceEqualOperator() {
        Observable<String> observable1 = Observable.from(fruits);
        Observable<String> observable2 = Observable.from(fruits).delay(3, TimeUnit.SECONDS);
        Observable<String> observable3 = Observable.from(colors);

        Observable.sequenceEqual(observable1, observable2).subscribe(equal -> Log.i("sequenceEqualOperator1", equal.toString()));
        Observable.sequenceEqual(observable1, observable3).subscribe(equal -> Log.i("sequenceEqualOperator2", equal.toString()));
    }

    private void skipUntilOperator() {
        Observable.interval(2, TimeUnit.SECONDS).skipUntil(Observable.interval(5, TimeUnit.SECONDS).take(2)).take(5).subscribe(time -> Log.i("skipUntilOperator", time + ""));
    }

    private void skipWhileOperator() {
        Observable.from(fruits).skipWhile(string -> !string.equals("watermelon")).subscribe(string -> Log.i("skipWhileOperator", string));
    }

    private void takeUntilOperator() {
        Observable.interval(1, TimeUnit.SECONDS).takeUntil(Observable.interval(5, TimeUnit.SECONDS).take(2)).subscribe(time -> Log.i("takeUntilOperator", time + ""));
    }

    private void takeWhileOperator() {
        Observable.from(fruits).takeWhile(string -> !string.equals("watermelon")).subscribe(string -> Log.i("takeWhileOperator", string));
    }

    /* MathematicalAndAggregateOperators */
    private void averageOperator() {
        MathObservable.averageInteger(Observable.just(1, 2, 3, 4, 5)).subscribe(result -> Log.i("averageOperator", String.valueOf(result)));
    }

    private void concatOperator() {
        Observable.concat(Observable.from(fruits), Observable.from(colors)).subscribe(s -> Log.i("concatOperator", s));
        Observable.concat(Observable.interval(1, TimeUnit.SECONDS).take(5), Observable.interval(3, TimeUnit.SECONDS).take(3)).subscribe(second -> Log.i("concatOperator2", second + ""));
    }

    private void countOperator() {
        Log.i("count", "count");
        Observable.from(fruits).count().subscribe(count -> Log.i("countOperator", count + ""));
    }

    private void maxOperator() {
        MathObservable.max(Observable.from(numbers)).subscribe(max -> Log.i("maxOperator", max + ""));
    }

    private void minOperator() {
        MathObservable.min(Observable.from(numbers)).subscribe(min -> Log.i("minOperator", min + ""));
    }

    private void reduceOperator() {
        Observable.from(numbers).reduce((integer1, integer2) -> integer1 > integer2 ? integer1 - integer2 : integer1 + integer2).subscribe(integer -> Log.i("reduceOperator", integer + ""));
        Observable.from(numbers).reduce((integer1, integer2) -> integer1 + integer2).subscribe(sum -> Log.i("reduceOperator2", sum + ""));//sum
    }

    private void sumOperator() {
        MathObservable.sumInteger(Observable.from(numbers)).subscribe(sum -> Log.i("sumOperator", sum + ""));
    }

    /* ConnectableObservableOperators */
    private void connectOperator() {
        ConnectableObservable<String> observable = Observable.from(fruits).publish();
        Log.i("connectOperator", "==subscribe==");
        observable.subscribe(string -> Log.i("connectOperator", string));
        Log.i("connectOperator", "==connect==");
        observable.connect();

    }

    private void publishOperator() {
        ConnectableObservable<Long> observable = Observable.interval(2, TimeUnit.SECONDS).take(5).publish();
        observable.subscribe(time -> Log.i("publishOperator1", time + ""));
        observable.subscribe(time -> Log.i("publishOperator2", time + ""));
        observable.connect();
        Observable.timer(5, TimeUnit.SECONDS).subscribe(aLong -> observable.subscribe(time -> Log.i("publishOperator3", time + "")));
        Observable.timer(8, TimeUnit.SECONDS).subscribe(aLong -> observable.subscribe(time -> Log.i("publishOperator4", time + "")));
    }

    private void refCountOperator() {
        ConnectableObservable<Long> observable = Observable.interval(2, TimeUnit.SECONDS).take(5).publish();

        observable.subscribe(o -> Log.i("refCount1", o.toString()), throwable -> {
        }, () -> Log.i("refCount1", "finish"));

        observable.connect();

        observable.subscribe(o -> Log.i("refCount2", o.toString()), throwable -> {
        }, () -> Log.i("refCount2", "finish"));
        observable.subscribe(o -> Log.i("refCount3", o.toString()), throwable -> {
        }, () -> Log.i("refCount3", "finish"));

        Observable.timer(7, TimeUnit.SECONDS).subscribe(aLong -> {
            observable.replay().subscribe(o -> Log.i("refCount4", o.toString()), throwable -> {
            }, () -> Log.i("refCount4", "finish"));
        });

    }

    private void replayOperator() {
        ConnectableObservable<Integer> observable = Observable.range(0, 5).replay(2);

        observable.subscribe(value -> {
            Log.i("subscriber1 ", value + "");
        });

        observable.subscribe(value -> {
            Log.i("subscriber2 ", value + "");
        });

        observable.connect();

        observable.subscribe(value -> {
            Log.i("subscriber3 ", value + "");
        });

        connectOperator();
        observable.subscribe(value -> {
            Log.i("subscriber4 ", value + "");
        });

        joinObservable();
        observable.subscribe(value -> {
            Log.i("subscriber5", value + "");
        });


    }
}
