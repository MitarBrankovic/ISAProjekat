Vue.component("Navbar", {
    data: function() {
        return {
			activeUser:null

        }
    },
    template:`  
    <nav class="topnav">
	<div v-if="(activeUser !== null) && (activeUser.role!=='cottage_owner')">
	  <a class="active" href="/#/">Home</a>
	</div>
	<div v-else>
	<a class="active" href="/#/">Home</a>
	</div>
	
		<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
			<a href="#/cottageOwnerHome">Moje vikendice</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
			<a href="#/cottageReservationHistory">Istorija rezervacija</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
			<a href="#/currentCottageAppointments">Rezervacije u toku</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
			<a href="#/finishingCottageReports">Izvestaj</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
					<a href="#/cottageProfitGraph">Profit</a>
			</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
					<a href="#/cottageRatingGraph">Ocene</a>
			</div>
			<div v-if="(activeUser !== null) && (activeUser.role==='cottage_owner')">
					<a href="#/cottageReservationGraph">Posecenost</a>
			</div>
		
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
			<a href="#/boatOwnerHome">Moji brodovi</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
			<a href="#/boatReservationHistory">Istorija rezervacija</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
			<a href="#/currentBoatReservations">Rezervacije u toku</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
			<a href="#/finishingBoatReports">Izvestaj</a>
		</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
					<a href="#/boatProfitGraph">Profit</a>
			</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
					<a href="#/boatRatingGraph">Ocene</a>
			</div>
		<div v-if="(activeUser !== null) && (activeUser.role==='ship_owner')">
					<a href="#/boatReservationGraph">Posecenost</a>
			</div>
		
		
		
		
		<div class="topnav-right dropdown1" v-if="(activeUser !== null) && (activeUser.role==='admin')">
			<button class="dropbtn1">Zahtevi
				<i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content1" >
				<div v-if="(activeUser.role==='admin')">
					<a href="#/requestDeleteAcc">Zahtevi za brisanje naloga</a>
				</div>
				<div v-if="(activeUser.role==='admin')">
					<a href="#/complaints">Žalbe</a>
				</div>
				<div v-if="(activeUser.role==='admin')">
					<a href="#/requestNewAcc">Zahtevi za dodavanje naloga</a>
				</div>
				<div v-if="(activeUser.role==='admin')">
					<a href="#/ratingApproval">Ocene</a>
				</div>
			</div>
		</div>

		<div class="topnav-right dropdown1" v-if="(activeUser !== null) && (activeUser.role==='client')">
			<button class="dropbtn1">Termini
				<i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content1" >
				<div v-if="(activeUser.role==='client')">
					<a href="#/scheduledAppointments">Zakazani termini</a>
					<a href="#/historyReservations">Istorija zakazanih termina</a>
					<a href="#/basicReservation">Zakazi termin</a>
				</div>
			</div>
		</div>

		<div class="topnav-right" v-if="(activeUser===null)">
	    	<a href="/#/login">Login</a>
	    	<a href="/#/register">Registruj se</a>
  		</div>

		<div class="topnav-right dropdown1" v-if="(activeUser !== null) && (activeUser.role !== 'admin') && (activeUser.role !== 'fishing_instructor')">
			<button class="dropbtn1">Profil
				<i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content1" >
				<div v-if="(activeUser.role==='client')">
					<a href="/#/profileClient">Moj profil</a>
					<a href="#/subscriptions">Pretplate</a>
					<a href="#/reportsOfClient">Penali</a>
					<a href="/#/sendComplaint">Napisi zalbu</a>
				</div>
				<div v-if="(activeUser.role==='cottage_owner')">
					<a href="/#/profileCottageOwner">Moj profil</a>
					<a href="/#/cottageOwnersCalendar">Kalendar zauzetosti</a>
				</div>
				<div v-if="(activeUser.role==='ship_owner')">
					<a href="/#/profileBoatOwner">Moj profil</a>
					<a href="/#/boatOwnersCalendar">Kalendar zauzetosti</a>
				</div>
				<a href="/#/logout">Logout</a>
			</div>
		</div>
		  
		<div class="topnav-right" v-if="(activeUser !== null)">
		    <a id="logout" class="nav-link" href="/#/logout">Logout <i class="bi bi-box-arrow-in-right"></i></a>
		</div>
		
	</div>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="/#/fishingInstructorsAdventures">Avanture</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="/#/profileFishingInstructor">Moj profil</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="/#/historyOfFishingAppointments">Sve rezervacije</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="/#/currentFishingAppointments">Rezervacije u toku</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="/#/instructorsGraph">Graf prihoda</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="/#/instructorsCalendar">Kalendar zauzetosti</a>
	<div v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')">
					<a href="#/fishingAdventureRatingGraph">Ocene</a>
			</div>
			<div v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')">
					<a href="#/fishingAdventureReservationGraph">Posecenost</a>
			</div>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/usersAdmin">Korisnici</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin') && (activeUser.adminType ==='main')" href="#/addAdmin">Novi admin</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/profileAdmin">Moj profil</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/penaltyApproval">Penali</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/adminsGraph">Graf prihoda</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/loyaltyProgramAdmin">Loyalty</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='fishing_instructor')" href="#/fishingInstructorsReports">Izveštaji</a>
	</nav>
    `       
        ,
    mounted(){
        this.activeUser=JSON.parse(localStorage.getItem('activeUser'))
    },
	methods:{

        
	}
});