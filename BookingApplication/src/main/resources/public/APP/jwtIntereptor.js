/*import axios from "axios";
var jwt = require('jsonwebtoken');
import App from "@/App";

export function jwtInterceptor(){
    axios.interceptors.request.use(async config => {
        const token = localStorage.jwt;
        if (token && !isTokenExpired(token)) {
            config.headers.common['Authorization'] = `Bearer ${token}`;
        } else {
            localStorage.removeItem('jwt');
            localStorage.removeItem('activeUser');
        }
        return config;
    });
}

export function isTokenExpired(token) {
    const payloadBase64 = token.split('.')[1];
    const decodedJson = Buffer.from(payloadBase64, 'base64').toString();
    const decoded = JSON.parse(decodedJson)
    const exp = decoded.exp;
    const expired = (Date.now() >= exp * 1000)
    return expired
}*/