/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * <h2><center>&copy; Copyright (c) 2020 STMicroelectronics.
  * All rights reserved.</center></h2>
  *
  * This software component is licensed by ST under BSD 3-Clause license,
  * the "License"; You may not use this file except in compliance with the
  * License. You may obtain a copy of the License at:
  *                        opensource.org/licenses/BSD-3-Clause
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "iwdg.h"
#include "gpio.h"
#include <stdio.h>

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */
#include "trace.h"
/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/

/* USER CODE BEGIN PV */

/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
/* USER CODE BEGIN PFP */

/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */

/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  MX_IWDG_Init();
  /* USER CODE BEGIN 2 */

  /* Do not remove this code below */
  MX_TRACE_Init();
  SDK_TRACE_Start();
  /* Do not remove this code from above */

  uint8_t value_a = 15;
  uint8_t value_b = 4;

  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_5, value_a & 0x01);
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_0, (value_a >> 1) & 0x01);
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_2, (value_a >> 2) & 0x01);
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_1, (value_a >> 3) & 0x01);

  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_6, value_b & 0x01);
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_9, (value_b >> 1) & 0x01);
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_7, (value_b >> 2) & 0x01);
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_8, (value_b >> 3) & 0x01);


  SDK_TRACE_Timestamp(P0, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_5));
  SDK_TRACE_Timestamp(P1, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_0));
  SDK_TRACE_Timestamp(P2, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_2));
  SDK_TRACE_Timestamp(P3, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_1));

  SDK_TRACE_Timestamp(P4, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_6));
  SDK_TRACE_Timestamp(P5, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_9));
  SDK_TRACE_Timestamp(P6, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_7));
  SDK_TRACE_Timestamp(P7, HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_8));

  uint8_t readed_value_a = HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_5);
  readed_value_a = readed_value_a | (HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_0) << 1);
  readed_value_a = readed_value_a | (HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_2) << 2);
  readed_value_a = readed_value_a | (HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_1) << 3);

  uint8_t readed_value_b = HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_6);
  readed_value_b = readed_value_b | (HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_9) << 1);
  readed_value_b = readed_value_b | (HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_7) << 2);
  readed_value_b = readed_value_b | (HAL_GPIO_ReadPin(GPIOB, GPIO_PIN_8) << 3);

  int result = (readed_value_a * readed_value_b) | (~ readed_value_b);
  SDK_TRACE_Print("a = %d, b = %d. Result (a*b)|~b: %d", readed_value_a, readed_value_b, result);


  /* Place your code before here */
  /* Do not remove this code below */
  SDK_TRACE_Stop();
  /* Do not remove this code from above */

  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
  while (1)
  {
    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage
  */
  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE1);
  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_LSI|RCC_OSCILLATORTYPE_HSE;
  RCC_OscInitStruct.HSEState = RCC_HSE_ON;
  RCC_OscInitStruct.LSIState = RCC_LSI_ON;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_ON;
  RCC_OscInitStruct.PLL.PLLSource = RCC_PLLSOURCE_HSE;
  RCC_OscInitStruct.PLL.PLLM = 25;
  RCC_OscInitStruct.PLL.PLLN = 336;
  RCC_OscInitStruct.PLL.PLLP = RCC_PLLP_DIV2;
  RCC_OscInitStruct.PLL.PLLQ = 4;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }
  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_PLLCLK;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV4;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV2;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_5) != HAL_OK)
  {
    Error_Handler();
  }
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */

  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     tex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */

/************************ (C) COPYRIGHT STMicroelectronics *****END OF FILE****/
