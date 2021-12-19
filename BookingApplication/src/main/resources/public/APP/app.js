const Home = { template: '<Home></Home>' }
const Register = { template: '<Register></Register>' }
const EmailVerification = { template: '<EmailVerification></EmailVerification>' }
const Login = { template: '<Login></Login>' }
const Logout = { template: '<Logout></Logout>' }
const ProfileClient = { template: '<ProfileClient></ProfileClient>' }
const Navbar = { template: '<Navbar></Navbar>' }
const SearchCottages = { template: '<SearchCottages></SearchCottages>' }
const SearchBoats = { template: '<SearchBoats></SearchBoats>' }
const SearchAdventures = { template: '<SearchAdventures></SearchAdventures>' }
const RequestDeleteAcc = { template: '<RequestDeleteAcc></RequestDeleteAcc>' }
const SendComplaint = { template: '<SendComplaint></SendComplaint>' }
const Complaints = { template: '<Complaints></Complaints>' }
const ProfileCottage = { template: '<ProfileCottage></ProfileCottage>'}
const ProfileBoat = {template : '<ProfileBoat></ProfileBoat>'}
const ProfileFishingInstructor = { template: '<ProfileFishingInstructor></ProfileFishingInstructor>' }
const FishingInstructorsAdventures = { template: '<FishingInstructorsAdventures></FishingInstructorsAdventures>' }
const SelectedFishingAdventure = {  template: '<SelectedFishingAdventure></SelectedFishingAdventure>' }
const ScheduledAppointments = {  template: '<ScheduledAppointments></ScheduledAppointments>' }
const SearchInstructorsAdventures = { template: '<SearchInstructorsAdventures></SearchInstructorsAdventures>' }
const HistoryReservations = {  template: '<HistoryReservations></HistoryReservations>' }
const SearchCottAppointments = {  template: '<SearchCottAppointments></SearchCottAppointments>' }
const SearchBoatAppointments = {  template: '<SearchBoatAppointments></SearchBoatAppointments>' }
const SearchAdvAppointments = {  template: '<SearchAdvAppointments></SearchAdvAppointments>' }

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
        { path: '/searchBoats', component: SearchBoats },
        { path: '/searchAdventures', component: SearchAdventures },
        { path: '/requestDeleteAcc', component: RequestDeleteAcc },
        { path: '/sendComplaint', component: SendComplaint },
        { path: '/complaints', component: Complaints },
		{ path: '/profileCottage', component: ProfileCottage },
		{ path: '/profileBoat', component: ProfileBoat },
        { path: '/profileFishingInstructor', component: ProfileFishingInstructor },
        { path: '/fishingInstructorsAdventures', component: FishingInstructorsAdventures },
        { path: '/selectedFishingAdventure', component: SelectedFishingAdventure },
        { path: '/scheduledAppointments', component: ScheduledAppointments },
        { path: '/searchInstructorsAdventures', component: SearchInstructorsAdventures },
        { path: '/historyReservations', component: HistoryReservations },
        { path: '/searchCottAppointments', component: SearchCottAppointments },
        { path: '/searchBoatAppointments', component: SearchBoatAppointments },
        { path: '/searchAdvAppointments', component: SearchAdvAppointments },

    ]

});

var app = new Vue({
    router,
    el: '#main-div',
    components: {
        vuejsDatepicker
    },
})