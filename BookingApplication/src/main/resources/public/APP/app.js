const Home = { template: '<Home></Home>' }
const Register = { template: '<Register></Register>' }
const EmailVerification = { template: '<EmailVerification></EmailVerification>' }
const Login = { template: '<Login></Login>' }
const Logout = { template: '<Logout></Logout>' }
const ProfileClient = { template: '<ProfileClient></ProfileClient>' }
const Navbar = { template: '<Navbar></Navbar>' }
const SearchCottages = { template: '<SearchCottages></SearchCottages>' }
const SearchOwnerCottage = { template: '<SearchOwnerCottage></SearchOwnerCottage>' }
const SearchOwnerBoat = {template: '<SearchOwnerBoat></SearchOwnerBoat>'}
const AddNewCottage = { template: '<AddNewCottage></AddNewCottage>' }
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
const HistoryOfFishingAppointments = {  template: '<HistoryOfFishingAppointments></HistoryOfFishingAppointments>' }
const ProfileAdmin = {template : '<ProfileAdmin></ProfileAdmin>'}
const AddAdmin = {template : '<AddAdmin></AddAdmin>'}
const UsersAdmin = {template : '<UsersAdmin></UsersAdmin>'}
const RequestNewAcc = { template: '<RequestNewAcc></RequestNewAcc>' }
const AdminChangePassword = { template: '<AdminChangePassword></AdminChangePassword>' }
const RatingApproval = { template: '<RatingApproval></RatingApproval>' }
const Subscriptions = {  template: '<Subscriptions></Subscriptions>' }
const BasicReservation = {  template: '<BasicReservation></BasicReservation>' }
const FishingInstructorsReports = {  template: '<FishingInstructorsReports></FishingInstructorsReports>' }
const PenaltyApproval = {  template: '<PenaltyApproval></PenaltyApproval>' }
const ProfileCottageOwner = { template: '<ProfileCottageOwner></ProfileCottageOwner>'}
const CottageOwnerHome = {template : '<CottageOwnerHome></CottageOwnerHome>'}
const ReportsOfClient = {  template: '<ReportsOfClient></ReportsOfClient>' }
const LoyaltyProgramAdmin = {  template: '<LoyaltyProgramAdmin></LoyaltyProgramAdmin>' }
const CurrentFishingAppointments = {  template: '<CurrentFishingAppointments></CurrentFishingAppointments>' }
const InstructorsCalendar = {  template: '<InstructorsCalendar></InstructorsCalendar>' }
const InstructorsGraph = {  template: '<InstructorsGraph></InstructorsGraph>' }
const BoatOwnersCalendar = {  template: '<BoatOwnersCalendar></BoatOwnersCalendar>' }
const CottageOwnersCalendar = {  template: '<CottageOwnersCalendar></CottageOwnersCalendar>' }
const AdminsGraph = {  template: '<AdminsGraph></AdminsGraph>' }
const CottageReservationHistory = { template: '<CottageReservationHistory></CottageReservationHistory>'}
const CurrentCottageAppointments = {template: '<CurrentCottageAppointments></CurrentCottageAppointments>'}
const CurrentBoatReservations = {template: '<CurrentBoatReservations></CurrentBoatReservations>'}
const BoatOwnerHome = {template: '<BoatOwnerHome></BoatOwnerHome>'}
const BoatReservationHistory = {template: '<BoatReservationHistory></BoatReservationHistory>'}
const ProfileBoatOwner = {template:'<ProfileBoatOwner></ProfileBoatOwner>'}
const FinishingCottageReports = {template:'<FinishingCottageReports></FinishingCottageReports>'}
const FinishingBoatReports = {template:'<FinishingBoatReports></FinishingBoatReports>'}
const CottageProfitGraph = {template:'<CottageProfitGraph></CottageProfitGraph>'}
const BoatProfitGraph = {template:'<BoatProfitGraph></BoatProfitGraph>'}
const BoatRatingGraph = {template:'<BoatRatingGraph></BoatRatingGraph>'}
const CottageRatingGraph = {template:'<CottageRatingGraph></CottageRatingGraph>'}
const FishingAdventureRatingGraph = {template:'<FishingAdventureRatingGraph></FishingAdventureRatingGraph>'}
const CottageReservationGraph = {template:'<CottageReservationGraph></CottageReservationGraph>'}
const BoatReservationGraph = {template:'<BoatReservationGraph></BoatReservationGraph>'}
const FishingAdventureReservationGraph = {template:'<FishingAdventureReservationGraph></FishingAdventureReservationGraph>'}

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
 		{ path: '/searchOwnerCottage', component: SearchOwnerCottage },
		{ path: '/addNewCottage', component: AddNewCottage },
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
        { path: '/historyOfFishingAppointments', component: HistoryOfFishingAppointments },
        { path: '/profileAdmin', component: ProfileAdmin },
        { path: '/addAdmin', component: AddAdmin },
        { path: '/requestNewAcc', component: RequestNewAcc },
        { path: '/adminChangePassword', component: AdminChangePassword },
        { path: '/usersAdmin', component: UsersAdmin },
        { path: '/ratingApproval', component: RatingApproval },
        { path: '/subscriptions', component: Subscriptions },
        { path: '/basicReservation', component: BasicReservation },
		{ path: '/fishingInstructorsReports', component: FishingInstructorsReports },
		{ path: '/penaltyApproval', component: PenaltyApproval },
		{ path: '/profileCottageOwner', component: ProfileCottageOwner },
		{ path: '/cottageOwnerHome' , component : CottageOwnerHome},
        { path: '/reportsOfClient', component: ReportsOfClient },	
		{ path: '/loyaltyProgramAdmin', component: LoyaltyProgramAdmin },
        { path: '/currentFishingAppointments', component: CurrentFishingAppointments },
        { path: '/instructorsCalendar', component: InstructorsCalendar },
        { path: '/instructorsGraph', component: InstructorsGraph },
        { path: '/boatOwnersCalendar', component: BoatOwnersCalendar },
        { path: '/cottageOwnersCalendar', component: CottageOwnersCalendar },
        { path: '/adminsGraph', component: AdminsGraph },
        { path: '/cottageReservationHistory', component: CottageReservationHistory },
        { path: '/currentCottageAppointments', component: CurrentCottageAppointments },
		{path: '/boatOwnerHome', component: BoatOwnerHome},
		{path: '/currentBoatReservations', component: CurrentBoatReservations},
		{path: '/boatReservationHistory', component: BoatReservationHistory},
		{path: '/profileBoatOwner', component: ProfileBoatOwner},
		{path: '/finishingCottageReports', component: FinishingCottageReports},
		{path: '/finishingBoatReports', component: FinishingBoatReports},
		{path: '/cottageProfitGraph', component: CottageProfitGraph},
		{path: '/boatProfitGraph', component: BoatProfitGraph},
		{path: '/boatRatingGraph', component: BoatRatingGraph},
		{path: '/cottageRatingGraph', component: CottageRatingGraph},
		{path: '/fishingAdventureRatingGraph', component: FishingAdventureRatingGraph},
    	{path: '/cottageReservationGraph', component: CottageReservationGraph},
		{path: '/boatReservationGraph', component: BoatReservationGraph},
		{path: '/fishingAdventureReservationGraph', component: FishingAdventureReservationGraph},
		{path: '/searchOwnerBoat', component: SearchOwnerBoat},
	]

});

var app = new Vue({
    router,
    el: '#main-div',
    components: {
        vuejsDatepicker
        
    },
})