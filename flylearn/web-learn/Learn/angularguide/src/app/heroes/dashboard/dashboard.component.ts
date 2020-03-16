import { Component, OnInit, Inject } from '@angular/core';
import { Hero } from '../../domain/Hero';
import { HeroesService } from '../heroes.service';
 
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  heroes: Hero[] = [];
 
  constructor(@Inject("heroesService")private heroesService) { }
 
  ngOnInit() {
    this.getHeroes();
  }
 
  getHeroes(): void {
    this.heroesService.getAllHero()
                       .subscribe(heroes => this.heroes = heroes.slice(1, 5));
  }
}