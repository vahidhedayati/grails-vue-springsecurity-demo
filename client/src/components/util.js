import $ from 'jquery';
const base = "http://www.abc.com/d/";
const ajaxPost = (api, data = null) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: base + api,
            data: data,
            type: "post",
            success: (d) => {
                resolve(d);
            },
            error: (err) => {
                reject(err);
            }
        })
    });
}
const ajaxGet = (api, data = null) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: base + api,
            data: data,
            type: "get",
            success: (d) => {
                resolve(d);
            },
            error: (err) => {
                reject(err);
            }
        })
    });
}

const formatCurrency = (num) => {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    let sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    let cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ',' +
        num.substring(num.length - (4 * i + 3));
    return ("￥" + ((sign) ? '' : '-') + num + '.' + cents);
}

const formatDate = (value) => {
    if (value === "") return "";
    let result = "";
    let year = value.getFullYear();
    let month = value.getMonth() + 1;
    let day = value.getDate();

    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }

    result = year + "-" + month + "-" + day;
    return result;
}

const telReg = /^[1]\d{10}$/;
const idCardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
const plateReg = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;

export {
    ajaxPost,
    ajaxGet,
    formatCurrency,
    formatDate,
    telReg,
    idCardReg,
    plateReg
}