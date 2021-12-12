const Home = { template: '<Home></Home>' }
const Register = { template: '<Register></Register>' }
const EmailVerification = { template: '<EmailVerification></EmailVerification>' }
const Login = { template: '<Login></Login>' }
const Logout = { template: '<Logout></Logout>' }
const ProfileClient = { template: '<ProfileClient></ProfileClient>' }
const Navbar = { template: '<Navbar></Navbar>' }
const SearchCottages = { template: '<SearchCottages></SearchCottages>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Home },
        { path: '/register', component: Register },
        { path: '/emailVerification', component: EmailVerification },
        { path: '/login', component: Login },
        { path: '/logout', component: Logout },
        { path: '/profileClient', component: ProfileClient },
        { path: '/navbar', component: Navbar },
        { path: '/searchCottages', component: SearchCottages },
    ]

});

var app = new Vue({
    router,
    el: '#main-div',
    components: {
        vuejsDatepicker
    },
})