/**
 * setting jquery-ui datepicker defaults
 */
//$.datepicker.setDefaults( $.datepicker.regional[ "zh-CN" ] );

$.datepicker.setDefaults({
//    dateFormat: "yy-mm-dd"
    showAnim: 'slideDown',
    showButtonPanel: true
});


function DatePickerUtil () {

}

DatePickerUtil.defaultOptions = {
    locale: 'zh-cn',
    format: 'LL',
    minDate: moment()
};

/**
 * Setup the relationships of two date input fields for Start date and Finish date
 * @param jqStartInput The JQuery object for start date
 * @param jqFinishInput The JQuery object for finish date
 */
DatePickerUtil.setupDateRange = function (jqStartInput, jqFinishInput) {
    jqStartInput.datepicker({
//        minDate: 0,
        onClose: function( selectedDate ) {
            jqFinishInput.datepicker( "option", "minDate", selectedDate );
        }
    });
    jqFinishInput.datepicker({
        defaultDate: "+1w",
//        minDate: +1,
//        numberOfMonths: 3,
        onClose: function( selectedDate ) {
            jqStartInput.datepicker( "option", "maxDate", selectedDate );
        }
    });
};

DatePickerUtil.getDate = function (jqDatePicker) {
    return DateTimeUtils.format(jqDatePicker.datepicker("getDate"));
};

/**
 *
 * @param jqDatePicker
 * @param date date object or date string
 * @returns {*}
 */
DatePickerUtil.setDate = function (jqDatePicker, date) {
    if (_.isString(date)) {
        date = moment(date).format(DateTimeUtils.formats.MOMENT_DATE);
        console.log('Value to be set to datepicker component: %s', date);
    }
    jqDatePicker.datepicker('setDate',date);
};



function DateTimeUtils() {}

DateTimeUtils.formats = {
    DATE_TIME: 'YYYY-MM-DD HH:mm:ss',
    MOMENT_DATE_TIME: 'YYYY-MM-DD HH:mm:ss',
    MOMENT_DATE:'YYYY-MM-DD',
    DATE_NLS: 'LL'
};
/**
 * Formats a date or a moment object to a string the server can understand
 * @param date javascript Date object or moment object
 * @param format
 * @returns {*}
 */
DateTimeUtils.format = function (date, format) {
    if(!date) return '';
    var _format = format || DateTimeUtils.formats.MOMENT_DATE_TIME;
    if (moment.isMoment(date)) {
        return date.format(_format);
    }else if(date instanceof Date) {
        return moment(date).format(_format);
    }

    return date;
};

DateTimeUtils.toLocalizedFormat = function (dateString) {
    moment.locale('zh-cn');
    return moment(dateString, DateTimeUtils.formats.DATE_TIME).format(DateTimeUtils.formats.DATE_NLS);
};
