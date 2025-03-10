# KUB Git Flow Guide • MVP

## Основная структура
Репозиторий использует упрощённую версию Git Flow для быстрой разработки MVP.

### Схема веток
```
main (protected)
  ↑
develop
  ↑
feature/mvp-auth     feature/mvp-profile     bugfix/mvp-login
```

При релизе:
```
main (protected) ←←←←←←←←←←←←←←
  ↑                          ↑
develop                     PR
  ↑
feature/mvp-auth → PR → develop
```

### Основные ветки
- `main` (protected branch) - содержит стабильный код для production
- `develop` - основная ветка разработки

### Feature ветки
Формат: `feature/KUB_XX`
Примеры:
- `feature/KUB_123`
- `feature/KUB_202`
Приемлемо, но **нежелательно**:
- `feature/mvp-auth`
- `feature/mvp-profile`
- `feature/mvp-payment`

### Bug fixes
Формат: bugfix/KUB_XX
Примеры: 
- `bugfix/KUB_123`
- `bugfix/KUB_202`
Приемлемо, но **нежелательно**:
- `bugfix/auth_jwt_refreshin

## Процесс разработки

### 1. Создание новой фичи
```bash
git checkout develop
git pull origin develop
git checkout -b feature/mvp-название-фичи
```

### 2. Работа над фичей
- Делаем коммиты в своей ветке
- Рекомендуется делать регулярные push на сервер
- Держим ветку в актуальном состоянии:
```bash
git checkout develop
git pull origin develop
git checkout feature/mvp-название-фичи
git merge develop
```

### 3. Создание Pull Request
- Push изменений в свою ветку
- Создаём PR из feature ветки в develop
- Дожидаемся code review
- После апрува мержим в develop

### 4. Релиз в production
- Создаём PR из develop в main
- После проверки на staging мержим в main
- Автоматический деплой через GitHub Actions

## Правила и соглашения

### Защищённые ветки
- `main` - protected branch
  - Запрещён прямой push
  - Требуется PR и code review
  - Должны проходить все CI проверки

### Именование веток
- Префикс `mvp-` для всех веток MVP-функционала
- Используем kebab-case
- Только английские названия
- Максимум 2-3 слова после mvp- в описании
- Всегда используем префиксы feature/ или bugfix/

### Коммиты
#### Формат
```
KUB_XX (scope) type: краткое описание
```

#### Типы коммитов (type)
- `feat:` - новая функциональность
- `fix:` - исправление багов
- `ref:` - рефакторинг
- `chore:` - конфигурации, зависимости

#### Scope (область изменений)
- `(auth)` - аутентификация
- `(api)` - API/сервисы
- `(ui)` - интерфейс
- `(store)` - стейт

#### Примеры коммитов
```
KUB_456 (api) fix: handle timeout
KUB_789 (ui) ref: optimize rendering
KUB_101 (store) feat: add user state

mvp (auth) feat: add google auth
```

#### Правила написания коммитов
- Описание на английском языке
- Используем present tense ("add", не "added")
- Не ставим точку в конце
- Максимум 72 символа в первой строке
- Для длинных описаний используем тело коммита

## CI/CD - в плане
- Автоматическая сборка при push в main
- Деплой в production после успешной сборки main
- Опционально: деплой в staging из develop

## Работа с ошибками и конфликтами
### При возникновении конфликтов
1. Обновить develop
2. Смержить develop в свою ветку
3. Разрешить конфликты локально
4. Протестировать после решения конфликтов

### При обнаружении ошибок в main
1. Создать bugfix/KUB_XX
2. После исправления — PR в develop
3. После тестирования — PR в main

## Контакты и поддержка
- Технические проблемы решаем в рабочем чате
- Code review проводим в GH, вопросы в топике чата
