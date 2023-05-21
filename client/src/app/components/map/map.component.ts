import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
declare var google: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  map: any
  searchBox: any
  form!: FormGroup
  placesService: any
  results: any[] = []
  markers: any[] = []
  userMarker: any

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.startMap()
    this.form = this.createForm()
    this.setupSearch()
    this.getUserLocation()
  }

  startMap() {
    const mapOptions = {
      center: new google.maps.LatLng(1.3521, 103.8198), // Set Singapore
      zoom: 12
    };

    this.map = new google.maps.Map(document.getElementById('map'), mapOptions);
    this.placesService = new google.maps.places.PlacesService(this.map);
  }

  setupSearch() {
    const input = document.getElementById('search-input') as HTMLInputElement;
    const autocomplete = new google.maps.places.Autocomplete(input);
    autocomplete.bindTo('bounds', this.map);

    autocomplete.addListener('place_changed', () => {
      const place = autocomplete.getPlace();
      if (!place.geometry || !place.geometry.location) {
        return;
      }
      this.displayNearest(place.geometry.location);
    });
  }

  private createForm(): FormGroup {
    return this.fb.group({
      searchTerm: ['', Validators.required]
    })
  }

  getUserLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const userLocation = new google.maps.LatLng(
            position.coords.latitude,
            position.coords.longitude
          );
          this.map.setCenter(userLocation);
          this.addUserMarker(userLocation);
        },
        (error) => {
          console.log('Error getting user location:', error);
        }
      );
    } else {
      console.log('Geolocation is not supported by this browser.');
    }
  }

  addUserMarker(location: any) {
    this.userMarker = new google.maps.Marker({
      position: location,
      map: this.map,
      title: 'Your Location'
    });
  }

  search() {
    if (this.form.invalid) {
      return;
    }

    const searchTerm = this.form.value.searchTerm;

    const request = {
      location: this.map.getCenter(),
      radius: 5000, 
      keyword: searchTerm
    };

    this.placesService.nearbySearch(request, (results: any[], status: any) => {
      if (status === google.maps.places.PlacesServiceStatus.OK) {
        this.results = results;
        this.displayNearestMarkers(results);
      }
    });
  }

  displayNearest(location: any) {
    this.clearMarkers();

    this.search();
  }

  displayNearestMarkers(results: any[]) {
    for (const result of results) {
      const marker = new google.maps.Marker({
        position: result.geometry.location,
        map: this.map,
        title: result.name
      });
      this.markers.push(marker);
      marker.addListener('click', () => {
        this.displayInfo(result, marker);
      });
    }
  }

  displayInfo(result: any, marker: any) {
    const infoWindow = new google.maps.InfoWindow({
      content: `<h3>${result.name}</h3>
                <p>${result.vicinity}</p>
                <p>${result.rating} stars</p>`
    });

    infoWindow.open(this.map, marker);
  }

  getPhoto(result: any): string {
    if (result.photos && result.photos.length > 0) {
      const photo = result.photos[0];
      return `${photo.getUrl({ maxWidth: 300 })}`;
    } else {
      return '../../assets/placeholder.jpg';
    }
  }

  clearMarkers() {
    for (const marker of this.markers) {
      marker.setMap(null);
    }
    this.markers = [];
  }
  
}


