
// const baseUrl = 'http://tastylove.xicp.net:25410/';
const baseUrl = 'http://localhost:8080/';
//用户id
let userId = "";
//EM:信访管理；LV:信访平台
const LV = "LV";
const EM = "EM";
//中心数据传输
const vueBus = new Vue();
//超时
axios.defaults.timeout = 3000;
//设置axios的公共
axios.defaults.baseURL = baseUrl;

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});