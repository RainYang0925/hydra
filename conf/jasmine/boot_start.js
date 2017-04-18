/**
 * Created by zhanghao on 4/18/16.
 */

//JSLog.info('Bootstrap file successful load');

(function(){
   // print('enter function');

    function extend(destination, source) {
        for (var property in source) destination[property] = source[property];
        return destination;
    }

    var jasmine = jasmineRequire.core(jasmineRequire);
    jasmineRequire.html(jasmine);
    var jasmineEnv = jasmine.getEnv();
    var jasmineInterface = jasmineRequire.interface(jasmine, jasmineEnv);
    var global = this;
    extend(global,jasmineInterface);

    var htmlReporter = new jasmine.HtmlReporter({
        env: jasmineEnv,
        timer: new jasmine.Timer()
    });

    jasmineEnv.addReporter(jasmineInterface.jsApiReporter);
    jasmineEnv.addReporter(htmlReporter);

    load(jsfilePath);
    jasmineEnv.execute();

})();


