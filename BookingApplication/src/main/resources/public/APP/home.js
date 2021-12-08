Vue.component("Home", {
    data: function() {
        return {
            cottages:true,
            ships:false,
            instructors:false,
        }
    },

template: ` 
	  <div>
        <div>
            <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="cottagesFun()">Cottages</button>
            <button class="bi bi-arrow-down btn btn-info" type="button" v-on:click="shipsFun()">Ships</button>
            <button class="bi bi-arrow-up btn btn-info" type="button" v-on:click="instructorsFun()">Instructors</button>
        </div>


        <div v-if="cottages">
        
        </div>





















        <div v-if="ships">
            <div style="margin-top: 2%;" class="container-fluid">
                <div style="margin-left: 5%;" class="row">
                    <div class="col col-sm-3">
                        <input type="text" class="form-control" placeholder="Naziv">
                    </div>
                    <div class="col col-sm-2">
                        <button type="button" v-on:click="searchRestaurant()" class="btn btn-danger">Pretrazi usluge</button>
                    </div>
                    <div class="col col-sm-4">
                    </div>
                    <div class="col col-sm-2">
                        <button type="button" v-on:click="searchRestaurant()" class="btn btn-danger">Dodaj novu uslugu</button>
                    </div>
                    <div class="col col-sm-1">
                    </div>
            </div>

            <div class="container-fluid" style="margin-top: 3%; margin-left: 5mm;">
                <div class="row row-cols-1 row-cols-md-4 g-4">
                    <div class="col">
                        <div class="card" style="width: 93%">
                            <img src="../images/ship.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">Ime usluge</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                                <li class="list-group-item">Adresa</li>
                                <li class="list-group-item">Max osoba</li>
                                <li class="list-group-item">4.5/5</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                            </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card" style="width: 93%">
                            <img src="../images/ship.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">Ime usluge</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                                <li class="list-group-item">Adresa</li>
                                <li class="list-group-item">Max osoba</li>
                                <li class="list-group-item">Rejting recimo</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                            </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card" style="width: 93%">
                            <img src="../images/ship.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">Ime usluge</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                                <li class="list-group-item">Adresa</li>
                                <li class="list-group-item">Max osoba</li>
                                <li class="list-group-item">Rejting recimo</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                            </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="card" style="width: 93%">
                            <img src="../images/ship.jpg" width="300" height="220" class="card-img-top" alt="...">
                            <div class="card-body">
                                <h5 class="card-title">Ime usluge</h5>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                                <li class="list-group-item">Adresa</li>
                                <li class="list-group-item">Max osoba</li>
                                <li class="list-group-item">Rejting recimo</li>
                            </ul>
                            <div class="card-body">
                                <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                                <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div v-if="instructors">
            instructors
        </div>


	  </div>
`

,
    methods: {
        cottagesFun: function(){
            this.cottages = true
            this.ships = false
            this.instructors = false
        },
        shipsFun: function(){
            this.cottages = false
            this.ships = true
            this.instructors = false
        },
        instructorsFun: function(){
            this.cottages = false
            this.ships = false
            this.instructors = true
        }
    },
    
    mounted() {

    }
});