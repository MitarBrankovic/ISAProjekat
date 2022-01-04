Vue.component("UsersAdmin", {
    data: function() {
        return {
            activeUser:"",
            users:null
        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle" style="color: #5cb85c;">Svi korisnici</h2>
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
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="u in users" v-if="u.id != activeUser.id && activeUser.adminType == 'main'">
	                        <td>{{u.name}} {{u.surname}}</td>
	                        <td v-if="u.role == 'fishing_instructor'">instruktor pecanja</td>
	                        <td v-if="u.role == 'cottage_owner'">vlasnik vikendice</td>
	                        <td v-if="u.role == 'ship_owner'">vlasnik broda</td>
	                        <td v-if="u.role == 'client'">klijent</td>
	                        <td v-if="u.role == 'admin'">administrator</td>
	                        <td>{{u.email}}</td>
	                        <td>{{u.address}}, {{u.city}}, {{u.country}}</td>
	                        <td>{{u.phoneNumber}}</td>
							<td><button class="btn btn-danger" type="button" v-on:click="deleteUser(u)">Obriši korisnika</button></td>
                        </tr>
                        <tr v-for="u in users" v-if="u.id != activeUser.id && activeUser.adminType == 'added' && u.role != 'admin'">
	                        <td>{{u.name}} {{u.surname}}</td>
	                        <td v-if="u.role == 'fishing_instructor'">instruktor pecanja</td>
	                        <td v-if="u.role == 'cottage_owner'">vlasnik vikendice</td>
	                        <td v-if="u.role == 'ship_owner'">vlasnik broda</td>
	                        <td v-if="u.role == 'client'">klijent</td>
	                        <td v-if="u.role == 'admin'">administrator</td>
	                        <td>{{u.email}}</td>
	                        <td>{{u.address}}, {{u.city}}, {{u.country}}</td>
	                        <td>{{u.phoneNumber}}</td>
							<td><button class="btn btn-danger" type="button" v-on:click="deleteUser(u)">Obriši korisnika</button></td>
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
            this.users = response.data
        })
        .catch(error=>{
            console.log("Greska.")	
            alert("Podaci su lose uneti.")
            window.location.reload()

        })
	},
    methods:{
        deleteUser:function(u){
            axios
            .post('/appUser/deleteUser', u)
            .then(response=>{
                this.users = response.data
            })
        },
    }
});
