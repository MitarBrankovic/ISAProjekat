Vue.component("BasicReservation", {
    data: function() {
        return {
            activeUser:"",
            stepper: 0,
            barProcentage: 25,
            times:[8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
            time:"",
            days:[1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
            day:1,
            numOfPeople:[4, 5, 6, 7, 8, 9, 10],
            num:"",
            datePick:"",
            radio:"vikendice",
            adventures:[],
            adventuresHelper:[],
            adventure:"",
            priceListAdventures:[],
            myPriceList:[],
            cottages:[],
            cottagesHelper:[],
            cottage:"",
            priceListCottages:[],
            boats:[],
            boatsHelper:[],
            boat:"",
            priceListBoats:[],

        }
    },
    template:`  
        <div style="margin-top: 30px;" class="container">

            <div class="progress">
                <div name="bar" class="progress-bar bg-success progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 25%">25%</div>
            </div>

            <div v-if="stepper == 0" style="margin-top: 50px;">
                <h2 class="centerIt" style="margin-bottom: 50px;">Izaberite datum i vreme</h2>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="vikendice" v-model="radio">
                    <label class="form-check-label" for="inlineRadio1">Vikendice</label>
                </div>

                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="brodovi" v-model="radio">
                    <label class="form-check-label" for="inlineRadio2">Brodovi</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="avanture" checked  v-model="radio">
                    <label class="form-check-label" for="inlineRadio3">Avanture</label>
                </div><br><br><br>

                <input id="datePickerId" v-model="datePick" type="date" name="trip-start" ></input>
                <select class="col-sm-2 col-form-select" v-model="time" requiered>
                    <option value="" selected disabled>Izaberite vreme</option>
                    <option v-for="c in times" :value="c">{{c}}:00</option>
                </select><br><br>

                <select class="col-sm-2 col-form-select" v-model="num" requiered>
                    <option value="" selected disabled>Izaberite broj osoba</option>
                    <option v-for="n in numOfPeople" :value="n">{{n}} osoba</option>
                </select><br><br>

                <select v-if="radio != 'avanture'" class="col-sm-2 col-form-select" v-model="day" requiered>
                    <option value="" selected disabled>Izaberite broj dana</option>
                    <option v-for="d in days" :value="d">{{d}} dana</option>
                </select><br><br>

                <div class="centerIt">
                    <button class="btn btn-success" type="button" v-on:click="firstNext()">Next</button>
                </div>
            </div>


            <div v-if="stepper == 1" style="margin-top: 50px;">

                <div v-if="radio =='vikendice'">
                    <SearchCottages id="search" @clicked="searchCottages"></SearchCottages>
                    <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                        <div class="row row-cols-1 row-cols-md-4 g-4">
                            <div class="col"  v-for = "a in cottages">
                                <div class="card" style="width: 93%">
                                    <img src="../images/cottage.jpg" width="200" height="120" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h5 class="card-title">{{a.name}}</h5>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">{{a.description}}</li>
                                        <li class="list-group-item">{{a.city}} {{a.address}}</li>
                                        <li class="list-group-item">Vlasnik: {{a.cottageOwner.name}} {{a.cottageOwner.surname}}</li>
                                        <li class="list-group-item">{{a.pricePerHour}}din/h</li>
                                        <li class="list-group-item">Maks broj ljudi: {{a.maxAmountOfPeople}}</li>
                                        <li class="list-group-item">Rating: {{a.rating}}</li>
                                    </ul>
                                    <div class="card-body">
                                        <button style="margin-left: 2%;" type="button" class="btn btn-success" v-on:click="addCottage(a)">Izaberi</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>

                <div v-else-if="radio =='brodovi'">
                    <SearchBoats id="search" @clicked="searchBoats"></SearchBoats>
                    <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                        <div class="row row-cols-1 row-cols-md-4 g-4">
                            <div class="col"  v-for = "a in boats">
                                <div class="card" style="width: 93%">
                                    <img src="../images/fishing.jpg" width="200" height="120" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h5 class="card-title">{{a.name}}</h5>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">{{a.description}}</li>
                                        <li class="list-group-item">{{a.city}} {{a.address}}</li>
                                        <li class="list-group-item">Vlasnik: {{a.shipOwner.name}} {{a.shipOwner.surname}}</li>
                                        <li class="list-group-item">{{a.pricePerHour}}din/h</li>
                                        <li class="list-group-item">Maks broj ljudi: {{a.maxAmountOfPeople}}</li>
                                        <li class="list-group-item">Rating: {{a.rating}}</li>
                                    </ul>
                                    <div class="card-body">
                                        <button style="margin-left: 2%;" type="button" class="btn btn-success" v-on:click="addBoat(a)">Izaberi</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>

                <div v-else-if="radio =='avanture'">
                    <SearchAdventures id="search" @clicked="searchAdventures"></SearchAdventures>
                    <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                        <div class="row row-cols-1 row-cols-md-4 g-4">
                            <div class="col"  v-for = "a in adventures">
                                <div class="card" style="width: 93%">
                                    <img src="../images/fishing.jpg" width="200" height="120" class="card-img-top" alt="...">
                                    <div class="card-body">
                                        <h5 class="card-title">{{a.name}}</h5>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">{{a.description}}</li>
                                        <li class="list-group-item">{{a.city}} {{a.address}}</li>
                                        <li class="list-group-item">Instructor: {{a.fishingInstructor.name}} {{a.fishingInstructor.surname}}</li>
                                        <li class="list-group-item">{{a.pricePerHour}}din/h</li>
                                        <li class="list-group-item">Maks broj ljudi: {{a.maxAmountOfPeople}}</li>
                                        <li class="list-group-item">Rating: {{a.rating}}</li>
                                    </ul>
                                    <div class="card-body">
                                        <button style="margin-left: 2%;" type="button" class="btn btn-success" v-on:click="addAdventure(a)">Izaberi</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="centerIt">
                    <button style="margin-right: 5px;" class="btn btn-secondary" type="button" v-on:click="stepperDown()">Nazad</button>
                    <button class="btn btn-success" type="button" v-on:click="secondNext()">Next</button>
                </div>
            </div>


            <div v-if="stepper == 2" style="margin-top: 50px;">
                <h2 class="centerIt">Dodatne usluge</h2>
                <div v-if="radio =='vikendice'" class="container-fluid" style="margin-top: 3%">
                    <table class="table">
                        <thead>
                            <tr>
                                <td scope="col">Opis</td>
                                <td scope="col">Cena</td>
                                <td scope="col"></td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="p in priceListCottages">
                                <td>{{p.description}}</td>
                                <td>{{p.price}} din.</td>
                                <td v-if="!isClicked(p)"><button type="button" v-on:click="addPriceList(p)" class="btn btn-success">Dodaj</button> </td>
                                <td v-if="isClicked(p)"><button class="btn btn-danger" type="button" v-on:click="removeFromPriceList(p)">Cancel</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div v-else-if="radio =='brodovi'" class="container-fluid" style="margin-top: 3%">
                <table class="table">
                    <thead>
                        <tr>
                            <td scope="col">Opis</td>
                            <td scope="col">Cena</td>
                            <td scope="col"></td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="p in priceListBoats">
                            <td>{{p.description}}</td>
                            <td>{{p.price}} din.</td>
                            <td v-if="!isClicked(p)"><button type="button" v-on:click="addPriceList(p)" class="btn btn-success">Dodaj</button> </td>
                            <td v-if="isClicked(p)"><button class="btn btn-danger" type="button" v-on:click="removeFromPriceList(p)">Cancel</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>

                <div v-else-if="radio =='avanture'" class="container-fluid" style="margin-top: 3%">
                    <table class="table">
                        <thead>
                            <tr>
                                <td scope="col">Opis</td>
                                <td scope="col">Cena</td>
                                <td scope="col"></td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="p in priceListAdventures">
                                <td>{{p.description}}</td>
                                <td>{{p.price}} din.</td>
                                <td v-if="!isClicked(p)"><button type="button" v-on:click="addPriceList(p)" class="btn btn-success">Dodaj</button> </td>
                                <td v-if="isClicked(p)"><button class="btn btn-danger" type="button" v-on:click="removeFromPriceList(p)">Cancel</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="centerIt">
                    <button style="margin-right: 5px;" class="btn btn-secondary" type="button" v-on:click="stepperDown()">Nazad</button>
                    <button class="btn btn-success" type="button" v-on:click="stepperUp()">Next</button>
                </div>
            </div>


            <div v-if="stepper == 3" style="margin-top: 50px;">
                <h2 class="centerIt">Finalna cena</h2>
                <div v-if="radio =='vikendice'" class="container" style="margin-top: 50px; margin-bottom: 50px;">
                    <table class="table">
                        <thead>
                            <tr>
                                <td scope="col">Opis</td>
                                <td scope="col">Cena</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><b>{{this.cottage.name}}</b></td>
                                <td>{{this.cottage.pricePerHour}} din/h</td>
                            </tr>
                            <tr v-for="p in myPriceList">
                                <td>{{p.description}}</td>
                                <td>{{p.price}} din.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div v-else-if="radio =='brodovi'" class="container" style="margin-top: 50px; margin-bottom: 50px;">
                    <table class="table">
                        <thead>
                            <tr>
                                <td scope="col">Opis</td>
                                <td scope="col">Cena</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><b>{{this.boat.name}}</b></td>
                                <td>{{this.boat.pricePerHour}} din/h</td>
                            </tr>
                            <tr v-for="p in myPriceList">
                                <td>{{p.description}}</td>
                                <td>{{p.price}} din.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>  

                <div v-else-if="radio =='avanture'" class="container" style="margin-top: 50px; margin-bottom: 50px;">
                    <table class="table">
                        <thead>
                            <tr>
                                <td scope="col">Opis</td>
                                <td scope="col">Cena</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><b>{{this.adventure.name}}</b></td>
                                <td>{{this.adventure.pricePerHour}} din/h</td>
                            </tr>
                            <tr v-for="p in myPriceList">
                                <td>{{p.description}}</td>
                                <td>{{p.price}} din.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>   

                <div v-if="radio =='vikendice'">
                    <h4>Termin pocinje {{datePick}} u {{time}}:00h, termin traje {{duration()}}h</h4>
                    <h4>Konacna cena je <b>{{finalPriceCottage()}}din.</b></h4>
                </div>

                <div v-else-if="radio =='brodovi'">
                    <h4>Termin pocinje {{datePick}} u {{time}}:00h, termin traje {{duration()}}h.</h4>
                    <h4>Konacna cena je <b>{{finalPriceBoat()}}din.</b></h4>
                </div>

                <div v-else-if="radio =='avanture'">
                    <h4>Termin pocinje {{datePick}} u {{time}}:00h, termin traje 3h.</h4>
                    <h4>Konacna cena je <b>{{finalPriceAdventure()}}din.</b></h4>
                </div>


                <div class="centerIt" style="margin-top: 50px;">
                    <button style="margin-right: 5px;" class="btn btn-secondary" type="button" v-on:click="stepperDown()">Nazad</button>
                    <button v-if="radio =='vikendice'" class="btn btn-success" type="button" v-on:click="reserveCottage()">Finish</button>
                    <button v-else-if="radio =='brodovi'" class="btn btn-success" type="button" v-on:click="reserveBoat()">Finish</button>
                    <button v-else-if="radio =='avanture'" class="btn btn-success" type="button" v-on:click="reserveAdventure()">Finish</button>
                </div>
            </div>
          
        
        </div>
    `       
        ,
    mounted(){
        this.activeUser = JSON.parse(localStorage.getItem('activeUser'))
        if(this.activeUser.role != 'client')
            tthis.$router.push('/')

        let myDate = new Date()
        myDate.setDate(myDate.getDate() + 1);
        datePickerId.min = myDate.toISOString().slice(0, -14);
	},
    methods:{
        firstNext() {
            if(this.datePick == "" || this.time == "" || this.num == ""){
                this.sweetError()
            }else{
                const dto = {
                    datePick: this.datePick,
                    time: this.time,
                    day: this.day,
                    num: this.num
                }
    
                if(this.radio == "vikendice"){
                    axios
                    .post('/cottageAppointments/getAllFreeCottages', dto)
                    .then(response=>{
                        this.cottages = response.data
                        this.cottagesHelper =response.data
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                    })
                }else if(this.radio == "brodovi"){
                    axios
                    .post('/boatAppointments/getAllFreeBoats', dto)
                    .then(response=>{
                        this.boats = response.data
                        this.boatsHelper =response.data
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                    })
                }else if(this.radio == "avanture"){
                    axios
                    .post('/fishingAppointments/getAllFreeAdventures', dto)
                    .then(response=>{
                        this.adventures = response.data
                        this.adventuresHelper =response.data
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                    })
                }
                this.stepperUp()
            }   
        },
        secondNext() {
            if(this.radio == "vikendice"){
                if(this.cottage == ""){
                    this.sweetError()
                }else{
                    axios
                    .get('/pricelist/getInstructorsPricelist/' + this.cottage.cottageOwner.id)
                    .then(response=>{
                        this.priceListCottages = response.data
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                        //window.location.reload()
                    })
                    this.stepperUp()
                }
            }else if(this.radio == "brodovi"){
                if(this.boat == ""){
                    this.sweetError()
                }else{
                    axios
                    .get('/pricelist/getInstructorsPricelist/' + this.boat.shipOwner.id)
                    .then(response=>{
                        this.priceListBoats = response.data
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                        //window.location.reload()
                    })
                    this.stepperUp()
                }
            }else if(this.radio == "avanture"){
                if(this.adventure == ""){
                    this.sweetError()
                }else{
                    axios
                    .get('/pricelist/getInstructorsPricelist/' + this.adventure.fishingInstructor.id)
                    .then(response=>{
                        this.priceListAdventures = response.data
                    })
                    .catch(error=>{
                        console.log("Greska.")	
                        alert("Podaci su lose uneti.")
                        //window.location.reload()
                    })
                    this.stepperUp()
                }
            }

        },
        stepperUp() {
            var bars = $('.progress-bar');
            for (i = 0; i < bars.length; i++) {
              console.log(i);
              var progress = $(bars[i]).attr('aria-valuenow');
              this.barProcentage +=25;
              progress = this.barProcentage;
              $(bars[i]).width(progress + '%');
              $(bars[i]).text(progress + "%");
            }
            this.stepper++
        },
        stepperDown() {
            var bars = $('.progress-bar');
            for (i = 0; i < bars.length; i++) {
                console.log(i);
                var progress = $(bars[i]).attr('aria-valuenow');
                this.barProcentage -=25;
                progress = this.barProcentage;
                $(bars[i]).width(progress + '%');
                $(bars[i]).text(progress + "%");
              }
            this.stepper--
        },
        searchCottages:function(search){
            console.log(search)
            axios
            .post('/cottages/searchCottages',search)
            .then(response=>{
                let listBackend = response.data
                let finalList = []
                for (const i in listBackend) {
                    for (const j in this.cottagesHelper) {
                        if(listBackend[i].id == this.cottagesHelper[j].id)
                            finalList.push(listBackend[i])
                    }
                }
                this.cottages = finalList
            })
        },
        searchBoats:function(search){
            console.log(search)
            axios
            .post('/boats/searchBoats',search)
            .then(response=>{
                let listBackend = response.data
                let finalList = []
                for (const i in listBackend) {
                    for (const j in this.boatsHelper) {
                        if(listBackend[i].id == this.boatsHelper[j].id)
                            finalList.push(listBackend[i])
                    }
                }
                this.boats = finalList
            })
        },
        searchAdventures:function(search){
            console.log(search)
            axios
            .post('/fishingAdventures/searchAdventures',search)
            .then(response=>{
                let listBackend = response.data
                let finalList = []
                for (const i in listBackend) {
                    for (const j in this.adventuresHelper) {
                        if(listBackend[i].id == this.adventuresHelper[j].id)
                            finalList.push(listBackend[i])
                    }
                }
                this.adventures = finalList
            })
        },
        addCottage(a){
            this.cottage = a
            this.myPriceList = []
            this.sweetAnimation()
        },
        addBoat(a){
            this.boat = a
            this.myPriceList = []
            this.sweetAnimation()
        },
        addAdventure(a){
            this.adventure = a
            this.myPriceList = []
            this.sweetAnimation()
        },
        addPriceList(p){
            this.myPriceList.push(p);
            this.sweetAnimation()
        },
        removeFromPriceList:function(p){
            var i = this.myPriceList.length
            while(i--){
                if(this.myPriceList[i].id == p.id){
                    this.myPriceList.splice(i,1)
                }
            }
        },
        isClicked:function(p){
            var exists = false
            for(var i = 0; i < this.myPriceList.length; i++){
                if(p.id == this.myPriceList[i].id){
                    exists = true
                    break
                }
            }
            return exists
        },
        finalPriceCottage(){
            let cottagePrice = this.cottage.pricePerHour * 24 * this.day
            let additionalPricing = 0
            for(var i = 0; i < this.myPriceList.length; i++){
                additionalPricing += this.myPriceList[i].price
            }
            return cottagePrice + additionalPricing
        },
        finalPriceBoat(){
            let boatPrice = this.boat.pricePerHour * 24 * this.day
            let additionalPricing = 0
            for(var i = 0; i < this.myPriceList.length; i++){
                additionalPricing += this.myPriceList[i].price
            }
            return boatPrice + additionalPricing
        },
        finalPriceAdventure(){
            let adventurePrice = this.adventure.pricePerHour * 3
            let additionalPricing = 0
            for(var i = 0; i < this.myPriceList.length; i++){
                additionalPricing += this.myPriceList[i].price
            }
            return adventurePrice + additionalPricing
        },
        duration(){
            return 24*this.day
        },
        reserveCottage(){
            let additionalPricing = 0
            let text = ""
            for(var i = 0; i < this.myPriceList.length; i++){
                additionalPricing += this.myPriceList[i].price
                text += this.myPriceList[i].description + ". "
            }
            const dto = {
                datePick: this.datePick,
                time: this.time,
                day: this.day,
                client: this.activeUser,
                cottage: this.cottage,
                totalPrice: additionalPricing + this.cottage.pricePerHour * 24 * this.day,
                additionalPricingText: text,
            }
            axios
            .post('/cottageAppointments/reserveCottage',dto,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.sweetAnimation()
                this.$router.push('/scheduledAppointments')
            })
        },
        reserveBoat(){
            let additionalPricing = 0
            let text = ""
            for(var i = 0; i < this.myPriceList.length; i++){
                additionalPricing += this.myPriceList[i].price
                text += this.myPriceList[i].description + ". "
            }
            const dto = {
                datePick: this.datePick,
                time: this.time,
                day: this.day,
                client: this.activeUser,
                boat: this.boat,
                totalPrice: additionalPricing + this.boat.pricePerHour * 24 * this.day,
                additionalPricingText: text,
            }
            axios
            .post('/boatAppointments/reserveBoat',dto,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.sweetAnimation()
                this.$router.push('/scheduledAppointments')
            })
        },
        reserveAdventure(){
            let additionalPricing = 0
            let text = ""
            for(var i = 0; i < this.myPriceList.length; i++){
                additionalPricing += this.myPriceList[i].price
                text += this.myPriceList[i].description + ". "
            }
            const dto = {
                datePick: this.datePick,
                time: this.time,
                client: this.activeUser,
                fishingAdventure: this.adventure,
                totalPrice: additionalPricing + this.adventure.pricePerHour * 3,
                additionalPricingText: text,
            }
            axios
            .post('/fishingAppointments/reserveAdventure',dto,{
                headers: {
                  'Authorization': `Bearer ${localStorage.jwt.slice(1,-1)}`
                },})
            .then(response=>{
                this.sweetAnimation()
                this.$router.push('/scheduledAppointments')
            })
        },
        sweetAnimation(){
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500,
                timerProgressBar: true,
                didOpen: (toast) => {
                  toast.addEventListener('mouseenter', Swal.stopTimer)
                  toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
              }) 
              Toast.fire({
                icon: 'success',
                title: 'Signed in successfully'
              })
        },
        sweetError(){
            Swal.fire({
                icon: 'error',
                title: 'Greska...',
                text: 'Popunite sva polja!',
              })
        }
    }
});
