const Home = { template: '<Home></Home>' }
const Register = { template: '<Register></Register>' }
const EmailVerification = { template: '<EmailVerification></EmailVerification>' }
const Login = { template: '<Login></Login>' }
const Logout = { template: '<Logout></Logout>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Home },
        { path: '/register', component: Register },
        { path: '/emailVerification', component: EmailVerification },
        { path: '/login', component: Login },
        { path: '/logout', component: Logout },
    ]

});

var app = new Vue({
    router,
    el: '#main-div',
    components: {
        vuejsDatepicker
    },
})