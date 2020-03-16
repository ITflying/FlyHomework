import { Injectable } from '@angular/core';
import { Http, Headers, Response} from '@angular/http';
import { Observable } from "rxjs/Rx";
import { Image } from "../domain/entities";

@Injectable()
export class BingImageService {
  imageUrl:string;
  headers=new Headers({
    'Content-Type': 'application/json',
    'Ocp-Apim-Subscription-Key':'enter-your-api-key-here'
  })

  constructor(private http:Http) {
    const searchContent = '动漫+墙纸';
    const baseUrl: String = `https://api.cognitive.microsoft.com/bing/v7.0/images/search`;
    this.imageUrl = baseUrl + `?q=${searchContent}&count=5&mkt=zh-CN&safesearch=Moderate`;
  }

  getImageUrl(): Observable<Image[]>{
    // 这里要用value来接收值
    return this.http.get(this.imageUrl, { headers: this.headers })
            .map(res => res.json().value as Image[])
            .catch(this.handleError);
  }

  private handleError(error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }

}
