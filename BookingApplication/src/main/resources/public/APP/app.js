const Home = { template: '<Home></Home>' }
const Register = { template: '<Register></Register>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Home },
        { path: '/register', component: Register }
    ]

});

var app = new Vue({
    router,
    el: '#main-div',
    components: {
        vuejsDatepicker
    },
})