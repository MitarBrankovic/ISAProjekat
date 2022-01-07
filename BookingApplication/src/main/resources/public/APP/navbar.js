Vue.component("Navbar", {
    data: function() {
        return {
			activeUser:null

        }
    },
    template:`  
    <nav class="topnav">
	  <a class="active" href="/#/">Home</a>
		<div v-if="(activeUser===null)">
			<a href="#">Something</a>
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

	  	<a href="#contact">Contact</a>
		<div class="topnav-right" v-if="(activeUser===null)">
	    	<a href="/#/login">Login</a>
	    	<a href="/#/register">Registruj se</a>
  		</div>

		<div class="topnav-right dropdown1" v-if="(activeUser !== null)">
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
				<div v-if="(activeUser.role==='fishing_instructor')">
					<a href="/#/fishingInstructorsAdventures">Avanture</a>
					<a href="/#/profileFishingInstructor">Moj profil</a>
					<a href="/#/historyOfFishingAppointments">Rezervacije</a>
				</div>
				<a href="/#/logout">Logout</a>
			</div>
		</div>
		  
		<div class="topnav-right" v-if="(activeUser !== null)">
		    <a id="logout" class="nav-link" href="/#/logout">Logout <i class="bi bi-box-arrow-in-right"></i></a>
		</div>
		<div class="topnav-right dropdown1" v-if="(activeUser !== null) && (activeUser.role==='admin')">
            <button class="dropbtn1">Something
            	<i class="fa fa-caret-down"></i>
            </button>

			<div class="dropdown-content1" >
				<div v-if="(activeUser.role==='admin')">
					<a class="nav-link" href="/#">Something</a>
				</div>
				<div v-if="(activeUser.role==='admin')">
					<a class="nav-link" href="/#">Something</a>
				</div>
			</div>
	  	</div>
	</div>
	
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/usersAdmin">Korisnici</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin') && (activeUser.adminType ==='main')" href="#/addAdmin">Novi admin</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/profileAdmin">Moj profil</a>
	<a v-if="(activeUser !== null) && (activeUser.role==='admin')" href="#/penaltyApproval">Penali</a>
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