
Handlebars.registerHelper('date', function (context, options) {
    if (!context) {//the input data is not valid
        return null;
    }
    var format = options.hash.format;
    if (!format) {
        format = DateTimeUtils.formats.MOMENT_DATE_TIME;
    }
    return moment(context).format(format);
});

Handlebars.registerHelper('isAfterDate', function (context, options) {
    if (!context) {//the input data is not valid
        return null;
    }
    var threshold = options.hash.threshold;

    var contextDate = moment(context).clone();
    var dynDate = moment(new Date()).add(threshold, 'days');
    if (dynDate.isAfter(contextDate) || dynDate.isSame(contextDate,'day')) {
        return options.fn(this);
    }else  {
        return options.inverse(this);
    }
});

Handlebars.registerHelper( 'getMapArrayValue', function ( context, key, max, options ) {
    var ret = "";
    var data=options.data;
    var mapValue = context[key];
    var arraySize = data.entry=mapValue.length;
    for(var i=0, j=mapValue.length; i<j; i++) {
        data.index=i;
        ret = ret + options.fn(mapValue[i]);
    }

    if (max && max > arraySize) {
        for(var k=0; k< max-arraySize; k++) {
            ret = ret + options.fn(k);
        }
    }
    return ret;
} );
Handlebars.registerHelper( 'counter', function ( context, options ) {
    var ret = "";
    var data=options.data;

    for(var i=0, j=context; i<j; i++) {
        data.index=i+1;
        ret = ret + options.fn(i);
    }
    return ret;
} );
