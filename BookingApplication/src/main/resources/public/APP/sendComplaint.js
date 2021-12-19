Vue.component("SendComplaint", {
    data: function() {
        return {
            activeUser:"",
            textAreaComplaint:"",
            cottages:[],
            boats:[],
            adventures:[],
            oneCottage:"",
            oneBoat:"",
            oneAdventure:"",

            cottagesButton: true,
            boatsButton: false,
            adventuresButton: false,
        }
    },
    template : ` 
    <div>
        <h1>Send complaint</h1><br><hr>
        
        <div>
            <h5><b>Izaberite za sta pisete zalbu:</b></h5>
            <label class="col-sm-1 col-form-label"><b>Cottages</b></label>
            <input checked type="radio" v-on:click="cottagesFun()" name="group1" style="margin-right:15px;">
            <label class="col-sm-1 col-form-label"><b>Boats</b></label>
            <input type="radio" v-on:click="boatsFun()" name="group1" style="margin-right:15px;">
            <label class="col-sm-1 col-form-label"><b>Adventures</b></label>
            <input type="radio" v-on:click="adventuresFun()" name="group1" style="margin-right:15px;">
        </div><hr><br><br>

        <div class="forma container" v-if="cottagesButton">
            <form id="editForm" method ="POST" @submit.prevent = "submitFormCott">

                <label class="col-sm-2 col-form-label" for="name"><b>Cottages</b></label>
                <select class="col-sm-2 col-form-select" v-model="oneCottage" requiered>
                    <option disabled value="">Please select one</option>
                    <option v-for="c in cottages" :value="c">{{c.name}} ({{c.cottageOwner.name}} {{c.cottageOwner.surname}})</option>
                </select><br><br>

			    <textarea class="col-sm-4 col-form-control" v-model="textAreaComplaint" id="deleteArea" placeholder="Write complaint text here..." rows="4" cols="50" required></textarea><br>
				<br>

                <button id="button1" type="submit" class="button">Send</button>
            </form>
        </div>


        <div class="forma container" v-if="boatsButton">
            <form id="editForm" method ="POST" @submit.prevent = "submitFormBoat">

                <label class="col-sm-2 col-form-label" for="name"><b>Boats</b></label>
                <select class="col-sm-2 col-form-select" v-model="oneBoat" requiered>
                    <option disabled value="">Please select one</option>
                    <option v-for="c in boats" :value="c">{{c.name}} ({{c.shipOwner.name}} {{c.shipOwner.surname}})</option>
                </select><br><br>

                <textarea class="col-sm-4 col-form-control" v-model="textAreaComplaint" id="deleteArea" placeholder="Write complaint text here..." rows="4" cols="50" required></textarea><br>
                <br>

                <button id="button1" type="submit" class="button">Send</button>
            </form>
        </div>
        



        <div class="forma container" v-if="adventuresButton">
            <form id="editForm" method ="POST" @submit.prevent = "submitFormAdv">

                <label class="col-sm-2 col-form-label" for="name"><b>Adventures</b></label>
                <select class="col-sm-2 col-form-select" v-model="oneAdventure" requiered>
                    <option disabled value="">Please select one</option>
                    <option v-for="c in adventures" :value="c">{{c.name}} ({{c.fishingInstructor.name}} {{c.fishingInstructor.surname}})</option>
                </select><br><br>

			    <textarea class="col-sm-4 col-form-control" v-model="textAreaComplaint" id="deleteArea" placeholder="Write complaint text here..." rows="4" cols="50" required></textarea><br>
				<br>

                <button id="button1" type="submit" class="button">Send</button>
            </form>
        </div>

    </div>	  
`
    	,
    methods: {
        cottagesFun: function(){
            this.cottagesButton = true
            this.boatsButton = false
            this.adventuresButton = false
        },
        boatsFun: function(){
            this.cottagesButton = false
            this.boatsButton = true
            this.adventuresButton = false
        },
        adventuresFun: function(){
            this.cottagesButton = false
            this.boatsButton = false
            this.adventuresButton = true
        },
        submitFormCott:function(){
            if(this.oneCottage){
                const complaintDto = {
                    text: this.textAreaComplaint,
                    entityId: this.oneCottage.id,
                    owner: this.oneCottage.cottageOwner.id,
                    client: this.activeUser
                }
    
                axios
                .post('/client/sendComplaint', complaintDto)
                .then(response=>{
                    this.$router.push('/')
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 3000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                          toast.addEventListener('mouseenter', Swal.stopTimer)
                          toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                      })                
                      Toast.fire({
                        icon: 'success',
                        title: 'Complaint sent successfully'
                      })
                })
                .catch(error=>{
                    console.log("Greska.")	
                    alert("Podaci su lose uneti.")
                    window.location.reload()
    
                })

            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Error...',
                    text: 'You need to select item from comboBox!',
                  })
            }
        },
        submitFormBoat:function(){
            if(this.oneBoat){
                const complaintDto = {
                    text: this.textAreaComplaint,
                    entityId: this.oneBoat.id,
                    owner: this.oneBoat.shipOwner.id,
                    client: this.activeUser
                }
    
                axios
                .post('/client/sendComplaint', complaintDto)
                .then(response=>{
                    this.$router.push('/')
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 3000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                          toast.addEventListener('mouseenter', Swal.stopTimer)
                          toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                      })                
                      Toast.fire({
                        icon: 'success',
                        title: 'Complaint sent successfully'
                      })
                })
                .catch(error=>{
                    console.log("Greska.")	
                    alert("Podaci su lose uneti.")
                    window.location.reload()
    
                })

            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Error...',
                    text: 'You need to select item from comboBox!',
                  })
            }
        },
        submitFormAdv:function(){
            if(this.oneAdventure){
                const complaintDto = {
                    text: this.textAreaComplaint,
                    entityId: this.oneAdventure.id,
                    owner: this.oneAdventure.fishingInstructor.id,
                    client: this.activeUser
                }
    
                axios
                .post('/client/sendComplaint', complaintDto)
                .then(response=>{
                    this.$router.push('/')
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 3000,
                        timerProgressBar: true,
                        didOpen: (toast) => {
                          toast.addEventListener('mouseenter', Swal.stopTimer)
                          toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }
                      })                
                      Toast.fire({
                        icon: 'success',
                        title: 'Complaint sent successfully'
                      })
                })
                .catch(error=>{
                    console.log("Greska.")	
                    alert("Podaci su lose uneti.")
                    window.location.reload()
    
                })

            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Error...',
                    text: 'You need to select item from comboBox!',
                  })
            }
        }
    },
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))

        axios.all([
            axios.get('/cottageAppointments/getFinishedCottagesByClient/' + this.activeUser.id),
            axios.get('/boatAppointments/getFinishedBoatsByClient/' + this.activeUser.id),
            axios.get('/fishingAppointments/getFinishedAdventuresByClient/' + this.activeUser.id)]).then(axios.spread((...responses) => {
           this.cottages = responses[0].data
           this.boats = responses[1].data
           this.adventures = responses[2].data
       }))
        
    },

});