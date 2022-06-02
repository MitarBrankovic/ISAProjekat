Vue.component("RequestNewAcc", {
    data: function() {
        return {
            activeUser:"",
            requests:null
        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle" style="color: #5cb85c;">Svi zahtevi za dodavanje naloga</h2>
            <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                        <td>Korisnik</td>
                        <td>Tip korisnika</td>
                        <td>E-mail</td>
                        <td>Adresa</td>
                        <td>Broj telefona</td>
                        <td></td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="r in requests" v-if="r.role != 'client' && r.role != 'admin' && r.verified == false">
	                        <td>{{r.name}} {{r.surname}}</td>
	                        <td v-if="r.role == 'fishing_instructor'">instruktor pecanja</td>
	                        <td v-if="r.role == 'cottage_owner'">vlasnik vikendice</td>
	                        <td v-if="r.role == 'ship_owner'">vlasnik broda</td>
	                        <td>{{r.email}}</td>
	                        <td>{{r.address}}, {{r.city}}, {{r.country}}</td>
	                        <td>{{r.phoneNumber}}</td>
	                        <td><button class="btn btn-success" type="button" v-on:click="acceptRequest(r)">Prihvati</button></td>
							<td><button class="btn btn-danger" type="button" v-on:click="declineRequest(r)">Odbij</button></td>
                        </tr>
                    </tbody>
                </table>
              </div>
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'admin')
        this.$router.push('/')
        axios
        .get('/appUser/getUsers')
        .then(response=>{
            this.requests = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Podaci su lose uneti.")
            window.location.reload()

        })
	},
    methods:{
        acceptRequest:function(r){
            axios
            .post('/admin/acceptNewAccount', r, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.requests = response.data
            })
        },
        declineRequest:function(r){
            axios
            .post('/admin/declineNewAccount', r, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.requests = response.data
            })
        }


    }
});
