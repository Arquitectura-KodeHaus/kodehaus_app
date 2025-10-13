import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PlanService } from '../services/plan.service';
import { Plan } from '../models/plan';

@Component({
  selector: 'app-planes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './planes.component.html',
  styleUrl: './planes.component.css'
})
export class PlanesComponent implements OnInit {
  planes: Plan[] = [];
  loading = false;
  showModal = false;
  editingPlan: Plan | null = null;
  showDeleteConfirm = false;
  planToDelete: Plan | null = null;

  // Form model for creating/editing plans
  planForm = {
    tipo: '',
    numModulos: 0,
    numUsuarios: 0,
    precio: 0
  };

  constructor(private planService: PlanService) {}

  ngOnInit() {
    this.loadPlanes();
  }

  loadPlanes() {
    this.loading = true;
    this.planService.getPlanes().subscribe({
      next: (planes) => {
        this.planes = planes;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading planes:', error);
        this.loading = false;
      }
    });
  }

  openCreateModal() {
    this.editingPlan = null;
    this.planForm = {
      tipo: '',
      numModulos: 0,
      numUsuarios: 0,
      precio: 0
    };
    this.showModal = true;
  }

  openEditModal(plan: Plan) {
    this.editingPlan = plan;
    this.planForm = {
      tipo: plan.tipo,
      numModulos: plan.numModulos,
      numUsuarios: plan.numUsuarios,
      precio: plan.precio
    };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.editingPlan = null;
    this.planForm = {
      tipo: '',
      numModulos: 0,
      numUsuarios: 0,
      precio: 0
    };
  }

  savePlan() {
    if (this.editingPlan) {
      // Update existing plan
      const updatedPlan: Plan = {
        ...this.editingPlan,
        ...this.planForm
      };
      
      this.planService.updatePlan(updatedPlan).subscribe({
        next: () => {
          this.loadPlanes();
          this.closeModal();
        },
        error: (error) => {
          console.error('Error updating plan:', error);
        }
      });
    } else {
      // Create new plan
      const newPlan: Plan = {
        id: 0, // Will be assigned by backend
        ...this.planForm
      };
      
      this.planService.createPlan(newPlan).subscribe({
        next: () => {
          this.loadPlanes();
          this.closeModal();
        },
        error: (error) => {
          console.error('Error creating plan:', error);
        }
      });
    }
  }

  confirmDelete(plan: Plan) {
    this.planToDelete = plan;
    this.showDeleteConfirm = true;
  }

  cancelDelete() {
    this.showDeleteConfirm = false;
    this.planToDelete = null;
  }

  deletePlan() {
    if (this.planToDelete) {
      this.planService.deletePlan(this.planToDelete.id).subscribe({
        next: () => {
          this.loadPlanes();
          this.cancelDelete();
        },
        error: (error) => {
          console.error('Error deleting plan:', error);
        }
      });
    }
  }

  formatPrice(price: number): string {
    return new Intl.NumberFormat('es-MX', {
      style: 'currency',
      currency: 'COP'
    }).format(price);
  }

  formatUsers(numUsuarios: number): string {
    return numUsuarios === 0 ? 'Ilimitado' : numUsuarios.toString();
  }

  getTotalValue(): number {
    return this.planes.reduce((sum, plan) => sum + plan.precio, 0);
  }

  getTotalModules(): number {
    return this.planes.reduce((sum, plan) => sum + plan.numModulos, 0);
  }
}
