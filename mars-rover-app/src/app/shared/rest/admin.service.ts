import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {PlateauCoord} from '../model/plateauCoord';
import {catchError} from 'rxjs/operators';
import {RoverCommand} from '../model/roverCommand';
import {EntirePlateau} from '../model/entirePlateau';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  readonly BASE_API_URL: string = 'api';
  readonly API_VERSION: string = 'v1';
  readonly PLATEAU_RESOURCE: string = 'plateaus';
  readonly ROVER_CREATION_RESOURCE: string = 'roverCreation';
  constructor(private http: HttpClient) { }
  private handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error} error message: ` + error.message);
    }
    // return an observable with a user-facing error message
    return throwError('Unable to process your request, please try again later.');
  }
  savePlateauDetails(plateauCoords: PlateauCoord): Observable<PlateauCoord> {
    return this.http.post<any>(`${this.BASE_API_URL}/${this.API_VERSION}/${this.PLATEAU_RESOURCE}`,
      plateauCoords)
      .pipe(
        catchError(this.handleError)
      );
  }
  updatePlateauDetails(roverCommand: RoverCommand): Observable<EntirePlateau> {
    console.log('hitting update');
    return this.http.post<any>(`${this.BASE_API_URL}/${this.API_VERSION}/${this.PLATEAU_RESOURCE}/${this.ROVER_CREATION_RESOURCE}`,
      roverCommand)
      .pipe(
        catchError(this.handleError)
      );
  }
}
