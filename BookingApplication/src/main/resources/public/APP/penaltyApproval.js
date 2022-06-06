Vue.component("PenaltyApproval", {
    data: function() {
        return {
            cottageReports: "",
            boatReports: "",
            fishingReports: "",
        }
    },
    template :`
    <div>
    <div class="container-fluid">
    <h2 style="margin-top: 1%; margin-bottom: 2%; color:#5cb85c">Penali - Vikendice</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Vikendica</td>
            <td>Adresa</td>
            <td>Datum i vreme početka rezervacije</td>
            <td>Trajanje</td>
            <td>Komentar</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Ocena</td>
            <td>Klijent</td>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="cottageReport in cottageReports">
            <td>{{cottageReport.appointment.cottage.name}}</td>
            <td>{{cottageReport.appointment.cottage.address}}</td>
            <td>{{cottageReport.appointment.appointmentStart.substring(8,10)}}.{{cottageReport.appointment.appointmentStart.substring(5,7)}}.{{cottageReport.appointment.appointmentStart.substring(0,4)}}. {{cottageReport.appointment.appointmentStart.substring(11,13)}}:{{cottageReport.appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{cottageReport.appointment.duration}} h</td>
            <td>{{cottageReport.comment}}</td>
            <td>{{cottageReport.appointment.extraNotes}}</td>
            <td>{{cottageReport.appointment.price}} din.</td>
            <td v-if="cottageReport.appointment.cottage.rating == 0">-/5</td>
            <td v-else>{{cottageReport.appointment.cottage.rating}}/5</td>
            <td>{{cottageReport.appointment.client.name}} {{cottageReport.appointment.client.surname}}</td>
            <td><button type="button" v-on:click="approveCottagePenalty(cottageReport)" class="btn btn-success">Odobri</button></td>
            <td><button type="button" v-on:click="declineCottagePenalty(cottageReport)" class="btn btn-danger">Odbij</button></td>
            </tr>
        </tbody>
    </table>
    </div>
    
    <div class="container-fluid">
    <h2 style="margin-top: 3%; margin-bottom: 2%; color:#5cb85c">Penali - Pecanje</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Avantura</td>
            <td>Adresa</td>
            <td>Datum i vreme početka rezervacije</td>
            <td>Trajanje</td>
            <td>Komentar</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Ocena</td>
            <td>Klijent</td>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="fishingReport in fishingReports">
            <td>{{fishingReport.appointment.fishingAdventure.name}}</td>
            <td>{{fishingReport.appointment.fishingAdventure.address}}, {{fishingReport.appointment.fishingAdventure.city}}</td>
            <td>{{fishingReport.appointment.appointmentStart.substring(8,10)}}.{{fishingReport.appointment.appointmentStart.substring(5,7)}}.{{fishingReport.appointment.appointmentStart.substring(0,4)}}. {{fishingReport.appointment.appointmentStart.substring(11,13)}}:{{fishingReport.appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{fishingReport.appointment.duration}} h</td>
            <td>{{fishingReport.comment}}</td>
            <td>{{fishingReport.appointment.extraNotes}}</td>
            <td>{{fishingReport.appointment.price}} din.</td>
            <td v-if="fishingReport.appointment.fishingAdventure.rating == 0">-/5</td>
            <td v-else>{{fishingReport.appointment.fishingAdventure.rating}}/5</td>
            <td>{{fishingReport.appointment.client.name}} {{fishingReport.appointment.client.surname}}</td>
            <td><button type="button" v-on:click="approveFishingPenalty(fishingReport)" class="btn btn-success">Odobri</button></td>
            <td><button type="button" v-on:click="declineFishingPenalty(fishingReport)" class="btn btn-danger">Odbij</button></td>
            </tr>
        </tbody>
    </table>
    </div>
    
    <div class="container-fluid">
    <h2 style="margin-top: 3%; margin-bottom: 2%; color:#5cb85c">Penali - Brodovi</h2>
    <table class="table">
        <thead>
        <tr>
            <td>Vikendica</td>
            <td>Adresa</td>
            <td>Datum i vreme početka rezervacije</td>
            <td>Trajanje</td>
            <td>Komentar</td>
            <td>Dodatne usluge</td>
            <td>Cena</td>
            <td>Ocena</td>
            <td>Klijent</td>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tbody>
            <tr v-for="boatReport in boatReports">
            <td>{{boatReport.appointment.boat.name}}</td>
            <td>{{boatReport.appointment.boat.address}}</td>
            <td>{{boatReport.appointment.appointmentStart.substring(8,10)}}.{{boatReport.appointment.appointmentStart.substring(5,7)}}.{{boatReport.appointment.appointmentStart.substring(0,4)}}. {{boatReport.appointment.appointmentStart.substring(11,13)}}:{{boatReport.appointment.appointmentStart.substring(14,16)}}</td>
            <td>{{boatReport.appointment.duration}} h</td>
            <td>{{boatReport.comment}}</td>
            <td>{{boatReport.appointment.extraNotes}}</td>
            <td>{{boatReport.appointment.price}} din.</td>
            <td v-if="boatReport.appointment.boat.rating == 0">-/5</td>
            <td v-else>{{boatReport.appointment.boat.rating}}/5</td>
            <td>{{boatReport.appointment.client.name}} {{boatReport.appointment.client.surname}}</td>
            <td><button type="button" v-on:click="approveBoatPenalty(boatReport)" class="btn btn-success">Odobri</button></td>
            <td><button type="button" v-on:click="declineBoatPenalty(boatReport)" class="btn btn-danger">Odbij</button></td>
            </tr>
        </tbody>
    </table>
    </div>
    
    
    </div>	  
    	`
    	,
    methods: {
        
        approveFishingPenalty(report) {
          axios
          .post("reports/approveFishingPenalty/",  report,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
          .then(response => {
          	  this.fishingReports = response.data
	          Swal.fire({ icon: 'success', title: 'Penal uspešno dodaljen klijentu.', showConfirmButton: false, timer: 2000 })
          })
        },
        
        approveBoatPenalty(report) {
          axios
          .post("reports/approveBoatPenalty/",  report,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
          .then(response => {
          	  this.boatReports = response.data
	          Swal.fire({ icon: 'success', title: 'Penal uspešno dodaljen klijentu.', showConfirmButton: false, timer: 2000 })
          })
        },
        
        approveCottagePenalty(report) {
          axios
          .post("reports/approveCottagePenalty/",  report,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
          .then(response => {
          	  this.cottageReports = response.data
	          Swal.fire({ icon: 'success', title: 'Penal uspešno dodaljen klijentu.', showConfirmButton: false, timer: 2000 })
          })
        },
        
        declineFishingPenalty(report) {
          axios
          .post("reports/declineFishingPenalty/",  report,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
          .then(response => {
          	  this.fishingReports = response.data
	          Swal.fire({ icon: 'error', title: 'Zahtev za penal klijentu odbijen.', showConfirmButton: false, timer: 2000 })
          })
        },
        
        declineBoatPenalty(report) {
          axios
          .post("reports/declineBoatPenalty/",  report,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
          .then(response => {
          	  this.boatReports = response.data
	          Swal.fire({ icon: 'error', title: 'Zahtev za penal klijentu odbijen.', showConfirmButton: false, timer: 2000 })
          })
        },
        
        declineCottagePenalty(report) {
          axios
          .post("reports/declineCottagePenalty/",  report,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
          .then(response => {
          	  this.cottageReports = response.data
	          Swal.fire({ icon: 'error', title: 'Zahtev za penal klijentu odbijen.', showConfirmButton: false, timer: 2000 })
          })
        },
        
        getFishingReports() {
        	axios
            .get("reports/getFishingReports")
	        .then(response => (this.fishingReports = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
        },
        
        getBoatReports() {
        	axios
            .get("reports/getBoatReports")
	        .then(response => (this.boatReports = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
        },
        
        getCottageReports() {
        	axios
            .get("reports/getCottageReports")
	        .then(response => (this.cottageReports = response.data))
            .catch(error=>{
                console.log("Greska.")	
                alert("Greska.")
                window.location.reload()
            })
        }
        
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin')
            this.$router.push('/')
        this.getCottageReports();
        this.getFishingReports();
        this.getBoatReports();

    },

});