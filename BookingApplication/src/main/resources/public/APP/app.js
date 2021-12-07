const Home = { template: '<Home></Home>' }
const Register = { template: '<Register></Register>' }
const EmailVerification = { template: '<EmailVerification></EmailVerification>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Home },
        { path: '/register', component: Register },
        { path: '/emailVerification', component: EmailVerification }
    ]

});

var app = new Vue({
    router,
    el: '#main-div',
    components: {
        vuejsDatepicker
    },
})