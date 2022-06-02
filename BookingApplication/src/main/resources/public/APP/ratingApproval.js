Vue.component("RatingApproval", {
    data: function() {
        return {
            activeUser:"",
            ratings:""
        }
    },
    template:`  
        <div style="margin-top: 30px;" v-if="activeUser.role=='admin'">
            <h2 class="flex title-div bigtitle" style="color: #5cb85c;">Svi zahtevi za odobravanje ocena</h2>
            <div class="container-fluid">
                <table class="table">
                    <thead>
                    <tr>
                    	<td>Vreme ocenjivanja</td>
                        <td>Klijent</td>
                        <td>E-mail klijenta</td>
                        <td>Entitet</td>
                        <td>Adresa entiteta</td>  
                        <td>Vlasnik entiteta</td>
                        <td>E-mail vlasnika</td>
                        <td>Ocena</td>
                        <td>Komentar</td>
                        <td></td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                        <tr v-for="r in ratings">
                        	<td>{{r.ratingDateTime.substring(8,10)}}.{{r.ratingDateTime.substring(5,7)}}.{{r.ratingDateTime.substring(0,4)}}. {{r.ratingDateTime.substring(11,13)}}:{{r.ratingDateTime.substring(14,16)}}</td>
	                        <td>{{r.clientName}}</td>
	                        <td>{{r.clientEmail}}</td>
	                        <td>{{r.entityName}}</td>
	                        <td>{{r.entityAddress}}</td>
	                        <td>{{r.ownerName}}</td>
	                        <td>{{r.ownerEmail}}</td>
	                        <td>{{r.rating}}</td>
	                        <td>{{r.comment}}</td>
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
        .get('/rating/getRatings')
        .then(response=>{
            this.ratings = response.data
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
            .post('/rating/approveRating', r, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.ratings = response.data
            })
        },
        declineRequest:function(r){
            axios
            .post('/rating/declineRating', r, {
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.ratings = response.data
            })
        }
    }
});
