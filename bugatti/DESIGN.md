# Design System Inspired by Bugatti

## 1. Visual Theme & Atmosphere

Bugatti.com does not behave like a website — it behaves like a feature-length car film that a visitor happens to be standing inside. The canvas is pure `#000000`, the only color that appears at rest is white, and the entire page is carried by full-bleed hero video and photography with a single typographic moment laid over the top. There are no cards, no grids, no promotional modules, no newsletter signups, no three-column editorial layouts. It is one continuous cinema-black channel, interrupted only by the cars themselves and a few pill-shaped calls to action that quietly say things like "EXPLORE OUR OPPORTUNITIES" in ALL CAPS monospace.

The single most distinctive move in the entire system is **scale**: the `Bugatti Display` typeface runs at **288px** at hero moments. Two hundred and eighty-eight pixels. That is not a typo — the dembrandt sweep extracted a heading style rendered at an 18rem size, ALL CAPS, line-height 1.0, meant to be read the way you read a brand mark on the front of a Chiron: from across a showroom floor. At 288px the headline is no longer text, it is architecture. The secondary display scale of 60px feels almost miniature next to it, and the 36px mid-display feels like fine print. This typographic hierarchy is the most extreme of any production brand website in this catalog, and it is what gives Bugatti.com its sculptural, couture-showroom presence.

The other signature is **monochromatic austerity**. The entire homepage uses exactly three colors at rest: `#000000`, `#ffffff`, and `#999999` (mid gray for disabled/tertiary states). There is no accent, no brand blue, no hazard color, no commerce orange, no gradient wash. The designers have made a conscious decision that Bugatti's color system should be the car paint itself — the page is a black velvet display stand, and the only color that exists is whatever blue-on-black lacquer the hero vehicle happens to be wearing today. This discipline is the exact opposite of PlayStation's PlayStation Blue or The Verge's Jelly Mint: Bugatti refuses to compete with its own product.

**Key Characteristics:**
- Cinema-black `#000000` canvas for the entire page — no gradients, no tints, no accents
- 288px `Bugatti Display` ALL-CAPS headline — the most extreme display scale in the catalog
- Three-font custom family: `Bugatti Display` (sculptural), `Bugatti Monospace` (UI labels), `Bugatti Text Regular` (body)
- Monochrome-only palette: black, white, and a single `#999999` mid gray for tertiary/disabled
- Pill buttons at `9999px` radius — transparent with a 1px white border, padding `12px 24px`
- Video- and photography-first page — the chrome is almost silent so the product can speak
- Mono UPPERCASE labels with 1.2–1.4px letter-spacing on every CTA, navigation link, and caption

## 2. Color Palette & Roles

### Primary
- **Velvet Black** (`#000000`): The entire canvas. Not near-black, not warm black — the pure HTML `#000`. Bugatti treats this as a display-stand surface, the way a jewelry brand treats a black velvet cloth.
- **Showroom White** (`#ffffff`): All text, all borders, all CTAs. White is the only color that appears at rest on the chrome. It has the weight of typeset print on a matte museum label.

### Secondary & Accent
- **Silver Mist** (`#999999`): The single gray in the system. Used for secondary button borders, disabled states, and the thinnest hairline dividers. Treat this as the "75%-volume" version of white — never a color, just a quieter version of the same voice.

### Surface & Background
- **Velvet Black** (`#000000`): The only surface. There is no secondary surface, no elevated card, no modal backdrop. If something needs to feel "separate", it sits on the same black and is marked with a thin `#999999` border — no color change.

### Neutrals & Text
- **Primary Text** (`#ffffff`): All headlines, body copy, button labels, and navigation items.
- **Tertiary Text** (`#999999`): Disclaimer text, placeholder labels, and the faintest supporting metadata. Used sparingly — Bugatti prefers to hide secondary content rather than dim it.

### Semantic & Accent
- **Tailwind Ring Leak** (`rgba(59, 130, 246, 0.5)`): A Tailwind default `--tw-ring-color` leaks into the extracted tokens from the build system — this is **not** part of the Bugatti brand palette. Ignore it. If a real focus state is needed, use a 1px `#ffffff` ring instead.

### Gradient System
None. There are zero decorative gradients on Bugatti.com. The only "gradient" on the page is whatever natural light gradient exists inside the hero video of the car itself. The brand refuses to apply any chrome gradient that could compete with the atmospheric lighting of the product photography.

## 3. Typography Rules

### Font Family
- **Bugatti Display** — fallback: `ui-sans-serif`, `system-ui`. A proprietary custom display typeface used only at very large sizes for hero and mid-display headlines. Designed to be read at architectural scale — at 288px, its geometry doubles as a visual element, not just text. The face carries a faint hint of early-20th-century Grand Prix typography (the period when Ettore Bugatti was racing) without ever becoming nostalgic.
- **Bugatti Monospace** — fallback: `ui-sans-serif`, `system-ui`. A custom monospaced face reserved for every UI label on the site. It handles all navigation links, all button labels, all captions, and all UPPERCASE metadata. The strict mono tracking (1.2–1.4px letter-spacing on all usages) gives the UI the appearance of a technical dossier or dashboard telemetry printout — appropriate for a company that builds 1600-horsepower hypercars.
- **Bugatti Text Regular** — fallback: `ui-sans-serif`, `system-ui`. The body copy workhorse, used for the rare paragraph and inline reading text. Weights and styles are restrained — this font exists to be invisible when the display type is shouting and the monospace is whispering.

### Hierarchy

| Role | Font | Size | Weight | Line Height | Letter Spacing | Notes |
|---|---|---|---|---|---|---|
| Hero Display (Monumental) | Bugatti Display | 288px / 18.00rem | 400 | 1.00 | — | ALL CAPS — the largest display scale in this catalog, architectural in presence |
| Mid Display (Feature) | Bugatti Display | 60px / 3.75rem | 400 | 1.00 | 1.4px | Feature-panel headlines, ALL CAPS optional |
| Mid Display (Subfeature) | Bugatti Display | 60px / 3.75rem | 400 | 1.00 | — | Secondary feature headlines |
| Section Heading | Bugatti Display | 36px / 2.25rem | 400 | 1.11 | — | Section-level title |
| Monumental Mono Headline | Bugatti Monospace | 60px / 3.75rem | 400 | 1.00 | — | UPPERCASE — reserved for technical/section labels at hero scale |
| Body Small (Display) | Bugatti Display | 16px / 1.00rem | 400 | 1.50 | — | Display face used sparingly at body size for marketing copy |
| Lead Body | Bugatti Text Regular | 20px / 1.25rem | 400 | 1.40 | — | Paragraph lead |
| Body Regular | Bugatti Text Regular | 16px / 1.00rem | 400 | 1.50 | — | Standard reading body |
| Body Compact | Bugatti Text Regular | 14px / 0.88rem | 400 | 1.43 | — | Dense body |
| UI Link (Caps) | Bugatti Monospace | 14px / 0.88rem | 400 | 1.43 | 1.4px | UPPERCASE — primary navigation and primary link style |
| UI Link (Mono Plain) | Bugatti Monospace | 14px / 0.88rem | 400 | 1.43 | — | Plain-case mono link — rare, used for disclaimer links |
| Button Label (CAPS) | Bugatti Monospace | 14px / 0.88rem | 400 | 1.43 | 1.4px | UPPERCASE — primary pill-button label |
| Button Label (Compact) | Bugatti Monospace | 12px / 0.75rem | 400 | 1.33 | 1.2px | UPPERCASE — small pill-button label |
| Button Label (Unstyled) | Bugatti Monospace | 12px / 0.75rem | 400 | 1.33 | — | Plain-case mono — footer microbutton |
| Caption CAPS Wide | Bugatti Monospace | 14px / 0.88rem | 400 | 1.43 | 1.4px | UPPERCASE — section eyebrows and tech-spec labels |
| Caption Plain Wide | Bugatti Monospace | 14px / 0.88rem | 400 | 1.43 | 1.4px | Plain-case with 1.4px tracking — the "mid-formal" register |
| Caption Plain | Bugatti Monospace | 14px / 0.88rem | 400 | 1.43 | — | Plain mono caption |
| Caption Micro (Text) | Bugatti Text Regular | 14px / 0.88rem | 400 | 1.43 | — | Body-face caption |
| Caption Micro (CAPS) | Bugatti Monospace | 12px / 0.75rem | 400 | 1.33 | 1.2px | UPPERCASE — smallest tagging label |
| Caption Micro (Plain) | Bugatti Monospace | 12px / 0.75rem | 400 | 1.33 | — | Smallest plain-case mono |

### Principles
- **Bugatti Display is a sculpture, not a font.** If you find yourself typesetting body copy or a button in Bugatti Display, you're using the wrong tool. Reserve this face for headlines at **36px minimum**, ideally 60px+, and at least once per page use it at 200px+ to create the monumental effect the brand is built around.
- **Bugatti Monospace owns the UI.** Every navigation link, every button, every caption, every eyebrow runs in Bugatti Monospace — usually UPPERCASE with 1.2–1.4px tracking. This mono-caps discipline is what makes the UI read like a Grand Prix telemetry panel rather than a luxury shopping cart.
- **Bugatti Text Regular is invisible.** It appears only in short paragraphs and inline reading copy, usually at 14–20px. It is never used for labels, buttons, or display.
- **There is no bold.** Every weight in the extracted tokens is regular (400). Bugatti does not use weight for hierarchy — it uses scale. When you need emphasis, make the type bigger, not heavier.
- **Tracking has two registers.** Mono caps always carry 1.2–1.4px letter-spacing. Display type at 60px+ sometimes carries 1.4px tracking at the hero scale. Body type has no tracking.
- **Line-height is brutally tight at display.** Every Bugatti Display usage runs at line-height 1.00 or 1.11. Headlines touch each other when they wrap — that's the design. Do not relax the leading.

### Note on Font Substitutes
The 1.00 line-height and 288px display scale both assume the **proprietary Bugatti Display face**, which is drawn with compact vertical metrics purpose-built for architectural scale. If you substitute with open-source extended geometric displays like **Unbounded**, **Big Shoulders Display**, or **Archivo Black**, make two adjustments: (1) **loosen line-height to ~1.05–1.10** to prevent ascender collisions, and (2) **cap the maximum display size at ~104–128px** on most viewports — these substitutes have wider horizontal metrics than Bugatti Display, so a 288px monumental headline will wrap across 4+ lines and overwhelm the layout. Reserve the 200px+ scale only for single-word monumental moments (e.g., "BUGATTI" alone). Bugatti Monospace substitutes (Space Mono, JetBrains Mono) and Bugatti Text Regular substitutes (Inter, DM Sans) work at the token values without adjustment.

## 4. Component Stylings

### Buttons

**Primary — White Outlined Pill**
- Background: transparent
- Text: `#ffffff`, Bugatti Monospace 14px / 400 / 1.4px tracking, UPPERCASE
- Border: `1px solid #ffffff`
- Border radius: `9999px` — full pill
- Padding: `12px 24px`
- Outline: `rgb(255, 255, 255) none 0px` at rest
- Hover: likely background fill to `#ffffff` with black text, or a subtle opacity dim (the extracted token set did not capture a bespoke hover — treat this as a safe assumption since the default Bugatti interaction is restraint)
- Active: opacity drop to ~0.7
- Focus: use a 1px `#ffffff` outer ring via `box-shadow: 0 0 0 1px #ffffff, 0 0 0 2px #000000` for contrast
- Transition: 200–300ms ease on background/color — quiet, never bouncy

**Secondary — Gray Rounded Button**
- Background: transparent
- Text: `#ffffff`, Bugatti Monospace 12px / 400 / 1.2px tracking, UPPERCASE
- Border: `1px solid #999999` (Silver Mist)
- Border radius: `6px` — subtle corner, the only non-pill non-zero radius in the system
- Padding: `6px 12px`
- Hover: border transitions to `#ffffff`, text stays white
- Active: opacity 0.7
- Used for compact utility buttons (menu toggles, closed-dialog buttons)

**Ghost — Unbordered Link Button**
- Background: transparent
- Text: `#ffffff`, Bugatti Monospace 12px / 400 — plain or UPPERCASE
- No border, no padding beyond inline
- Used in the footer and tertiary nav

### Cards & Containers
- **There are no cards.** Bugatti.com has no card component. The entire page is a sequence of full-bleed media blocks with a headline and optional CTA overlaid — more akin to a film chapter than a card grid.
- The closest thing to a "container" is the rare bordered section that uses a `1px solid #999999` frame, a `6px` border radius, and `#000000` interior. These are reserved for cookie-consent notices and modal-style dialogues, not editorial content.
- Hover state on media blocks: none. The video plays, the CTA becomes clickable, and that is the entire interaction vocabulary.

### Inputs & Forms
- The extracted tokens captured **zero input styles** (`⚠ Inputs: 0 styles`). This is because Bugatti.com has essentially no forms on the homepage — no newsletter signup, no search bar, no contact form, no email capture. When forms do appear (on deeper pages), apply these defaults consistent with the rest of the system:
  - **Default**: `#000000` background, `1px solid #999999` border, `6px` radius, `#ffffff` text in Bugatti Text Regular 16px, placeholder `#999999`.
  - **Focus**: border transitions to `#ffffff`, no glow — the border change IS the focus signal.
  - **Error**: border stays white; add a `#999999` inline message below. Bugatti does not use red error colors — it stays in the monochrome palette.
  - **Transition**: ~250ms ease on border-color.

### Navigation

- **Top nav**: black (`#000000`) thin strip with the Bugatti "EB" monogram or full "BUGATTI" wordmark centered, a hamburger "MENU" link left, and a "STORE" link right. Both nav links are Bugatti Monospace 14px UPPERCASE with 1.4px tracking.
- **Logo**: 128×29px at desktop scale — smaller than nearly every other brand in this catalog. Bugatti does not need to shout its name.
- **Hover on nav links**: color stays `#ffffff` — the hover signal is a subtle text-decoration underline or an opacity shift to ~0.75. No color change.
- **Mobile**: the full nav collapses to just three elements — "MENU", the wordmark, and "STORE" — which is basically the desktop layout minus the separator spacing.
- **Sticky behavior**: the nav is pinned at the top on scroll and stays black-on-black. When it overlaps a dark video, it becomes nearly invisible, which is by design.

### Image & Video Treatment
- **Aspect ratios**: 16:9 and 21:9 for hero video, 4:3 for mid-feature photography, 1:1 for rare portrait shots.
- **Corners**: rare — most media is full-bleed with zero border radius. When radius appears, it's `6px`.
- **Full-bleed**: yes, always. The hero video fills the viewport. Secondary feature video fills 100% of the section width.
- **Captions**: Bugatti Monospace 12px UPPERCASE in `#ffffff` at ~1.2px tracking, placed below the media or in the lower-left corner.
- **Hover**: no zoom, no scale, no scrim. The video plays, that is the hover state.
- **Lazy loading**: `loading="lazy"` on every image below the fold; hero video is preloaded.

### Atmospheric Overlay
- When type sits over photography or video that might threaten legibility, Bugatti uses a subtle `rgba(0, 0, 0, 0.4)` linear gradient from bottom (40% black) to top (transparent) — the only "shadow-like" effect in the system. It's a vignette, not a drop shadow.

## 5. Layout Principles

### Spacing System
- **Base unit**: 8px.
- **Scale** (from tokens): 4, 6, 12, 36, 48, 64px. Six values. **Six.** This is one of the smallest spacing scales of any major brand site — Bugatti uses a handful of discrete gaps and refuses to invent in-between values.
- **Section padding**: typically 48–64px vertical. Hero panels are full-viewport-height, which bypasses the scale entirely.
- **Button padding**: 6px 12px (compact) or 12px 24px (primary). Nothing else.
- **Inline spacing**: 4–12px between stacked labels; the big jump to 36/48/64 happens between content blocks.

### Grid & Container
- **Max width**: 1720px (dembrandt detected breakpoints up to 1720). The site scales to ultra-wide for luxury showroom displays and wide cinema monitors.
- **Column patterns**: there is essentially no multi-column grid on the homepage — it is a stack of single full-width blocks. When deeper pages need columns (configurator, atelier, technical specs), they use a 12-column Tailwind-based grid.
- **Outer padding**: minimal. Most sections bleed to the viewport edge, with padding only applied to the overlaid text and CTA block (typically 48–64px from the bottom-center).

### Whitespace Philosophy
Bugatti's whitespace philosophy is **cinematic negative space** — the page is 90% empty even when content is present, because the content is usually a video or photograph of a single car. The rhythm is: full-bleed media → monumental headline → single pill CTA → scroll → next full-bleed media. There is no "information density" anywhere. The page breathes the way a museum breathes, with each exhibit getting its own silent room.

### Border Radius Scale
- **0** — default for all media and the hero canvas
- **6px** — secondary rounded buttons, bordered frames, small utility containers
- **9999px** — primary pill buttons

Three values. No `12px`, no `24px`, no `20px`. Bugatti's radius system is the most restrained of any site in this catalog — the brand has made an active decision that "slightly rounded rectangle" is a vulgar shape, and committed to either true rectangle or true pill.

## 6. Depth & Elevation

| Level | Treatment | Use |
|---|---|---|
| 0 | No shadow, no border | Default text and media on `#000000` |
| 1 | `1px solid #999999` | Secondary containers, cookie-style dialogs |
| 2 | `1px solid #ffffff` | Primary button outline, active state indicators |
| 3 | Bottom-to-top `rgba(0, 0, 0, 0.4) → transparent` vignette | Text-legibility gradient when type sits over video |

**That is the entire depth system.** There are 1 shadows in the extracted token set (zero meaningful `box-shadow` values — just a placeholder). Bugatti does not use drop shadows. It does not use elevation rings. It does not use glowing focus states. Depth is implied by the 1px hairline of a border or the presence of a vignette gradient — nothing more.

### Decorative Depth
None. Zero gradients (except the subtle text-legibility vignette), zero blurs, zero glows, zero atmospheric effects. The decorative depth of Bugatti's site comes entirely from the lighting baked into the product photography. The chrome does not compete.

## 7. Do's and Don'ts

### Do
- **Do** keep the entire canvas `#000000`. No off-black, no near-black, no warm black. Bugatti is pure black.
- **Do** use Bugatti Display at architectural scale — minimum 36px, ideally 60px+, and once per page land a monumental 200px+ headline.
- **Do** use Bugatti Monospace UPPERCASE with 1.2–1.4px tracking for every button, link, nav item, and caption.
- **Do** use only white text at rest. `#999999` is only for disabled, tertiary, and thin borders.
- **Do** use 9999px border radius for primary buttons — full pill, thin 1px white outline, transparent fill.
- **Do** use full-bleed video and photography for every hero section. The product is the UI.
- **Do** maintain line-height 1.00–1.11 on display headlines. Tight leading is the architecture.
- **Do** treat whitespace like cinematic negative space — give every block its own silent room.

### Don't
- **Don't** introduce accent colors. No blue, no red, no commerce orange, no hover cyan, no warning red. The palette is black, white, and one gray.
- **Don't** use bold weights for hierarchy. Scale is the only hierarchy device — make it bigger, not heavier.
- **Don't** use drop shadows on any element. Bugatti has no `box-shadow` in its chrome.
- **Don't** use cards or elevated surfaces. Bugatti has no card component.
- **Don't** use rounded rectangles between 6px and 9999px. The radius system is rectangle, slightly-rounded utility, or full pill — nothing in between.
- **Don't** use Bugatti Display for body, buttons, or UI labels. Reserve it for headlines at 36px+.
- **Don't** use Bugatti Monospace in lowercase for primary UI. Buttons and nav links are always ALL CAPS.
- **Don't** add gradients, glows, blurs, or glassmorphism anywhere. The chrome is silent.
- **Don't** put text over photography without a `rgba(0, 0, 0, 0.4)` bottom-up vignette if legibility is at risk.

## 8. Responsive Behavior

### Breakpoints

| Name | Width | Key Changes |
|---|---|---|
| Mobile | <640px | Single column, hamburger "MENU", hero video locked to 9:16 or 16:9, hero headline scales to ~48–72px |
| Small Tablet | 640–767px | Still single column, padding opens slightly, typography scales up |
| Tablet | 768–1023px | Still single column for content, nav expands to include wordmark, headline scales to ~120px |
| Small Desktop | 1024–1279px | Full desktop nav, headline scales to ~200px |
| Desktop | 1280–1535px | Full layout, headline at 240–260px |
| Large Desktop | 1536–1719px | Max headline scale (288px), ultra-wide hero video |
| Ultra-Wide | ≥1720px | Container caps, hero video locks at 21:9 or wider, everything else stays proportional |

The dembrandt sweep detected 6 breakpoints (1720 → 1536 → 1280 → 1024 → 768 → 640). This is a narrower responsive set than PlayStation's 30 — Bugatti tunes for six clean thresholds rather than micro-adjusting every device boundary. The brand's assumption is that its visitors are either on a high-end laptop, a desktop monitor, or a phone, and the site doesn't need to fuss over everything in between.

### Touch Targets
- Primary pill buttons are `12px 24px` padded with 14px text — approximately 38–42px tall. **This falls slightly below WCAG AAA 44px recommendations**. For derivative work, bump vertical padding to 14–16px to hit 44px+.
- Secondary buttons at `6px 12px` padding are about 28–32px tall — definitely below touch-target minimums. Use these only on desktop pointer contexts.
- Navigation links have no explicit padding — the tap area is the text box, which at 14px is too small. Add `12–14px` vertical padding on mobile to make them touchable.

### Collapsing Strategy
- **Nav**: desktop shows `MENU / BUGATTI wordmark / STORE`. Mobile keeps the same layout — there is no drawer, because there are only three items.
- **Grid**: no grid to collapse. The page is already single-column at every breakpoint.
- **Spacing**: section padding tightens from 64 → 48 → 36 → 12px as viewport narrows.
- **Type**: Bugatti Display scales from 288px → 200px → 120px → 60px → 48px as viewport narrows. The scale curve is aggressive — losing 240px between the max and mobile hero.
- **Video**: art-direction swap between 21:9 desktop and 16:9 or 9:16 mobile hero cuts.

### Image & Video Behavior
- Hero video uses adaptive bitrate streaming and `poster=` fallback.
- Below-the-fold media uses `loading="lazy"` with `srcset` art direction.
- Bugatti serves high-density imagery through `imgix` — you'll see `bugatti.imgix.net` URLs with transformation parameters.

## 9. Agent Prompt Guide

### Quick Color Reference
- **Primary Canvas**: "Velvet Black (`#000000`)"
- **Primary Text**: "Showroom White (`#ffffff`)"
- **Secondary Text / Disabled / Hairline Border**: "Silver Mist (`#999999`)"
- **Accent**: None. Do not add one.
- **Hover Signal**: Opacity shift or border-color shift — no color change

### Example Component Prompts
1. *"Create a monumental hero headline using Bugatti Display at 288px, ALL CAPS, `#ffffff` text on a pure `#000000` canvas, line-height 1.0, no letter-spacing. Place a full-bleed 21:9 hero video behind it with a `rgba(0, 0, 0, 0.4) → transparent` bottom-up vignette for legibility."*
2. *"Design a primary pill CTA button: transparent background, 1px solid `#ffffff` border, `9999px` border radius, 12px × 24px padding, Bugatti Monospace 14px / 400 / 1.4px letter-spacing UPPERCASE label in `#ffffff`. Hover state fills the background white with black text, 250ms ease."*
3. *"Build a navigation bar: pure `#000000` background, `MENU` link left, centered `BUGATTI` wordmark (128×29px), `STORE` link right. All links in Bugatti Monospace 14px UPPERCASE with 1.4px letter-spacing in `#ffffff`. No dividers, no hover color — just a slight opacity dim on hover."*
4. *"Create a mid-feature section heading: Bugatti Display 60px ALL CAPS in `#ffffff`, line-height 1.0, centered over a full-bleed photograph. Place a single primary pill CTA 48–64px below the headline."*
5. *"Design a secondary utility button for a cookie dialog: transparent background, 1px solid `#999999` border, 6px border radius, 6px × 12px padding, Bugatti Monospace 12px / 400 / 1.2px tracking UPPERCASE label in `#ffffff`."*

### Iteration Guide
When refining existing screens generated with this design system:
1. **Audit the canvas.** If the background isn't pure `#000000`, change it. Bugatti does not tolerate off-black.
2. **Audit the palette.** Any color that isn't `#000000`, `#ffffff`, or `#999999` is drift. Remove it — that includes ALL accent colors, including common defaults like `#0070cc` Tailwind blue.
3. **Audit display scale.** If the largest headline on a page is smaller than 60px, it's under-scaled. Bugatti's minimum "monumental moment" is 60px; the maximum is 288px. Aim for the upper half.
4. **Audit mono-caps discipline.** Every button, every nav link, every caption, every CTA should be Bugatti Monospace UPPERCASE with 1.2–1.4px letter-spacing. If you see sentence case or mixed case on a button, that's drift.
5. **Audit shadows and gradients.** Strip every `box-shadow`. Strip every gradient except the one legibility vignette over video. Bugatti's chrome is silent.
6. **Audit radius.** Every container should land on `0`, `6px`, or `9999px`. If you see `12px`, `16px`, `20px`, `24px`, correct to the nearest Bugatti value (almost always `6px` or `9999px`).
7. **Audit type weight.** All weights should be 400. If you see `bold` or `700` anywhere, change it. Scale, not weight, is the hierarchy.
8. **Audit whitespace.** If a section feels cramped, add 48–64px. If it feels airy, leave it — Bugatti's negative space is a feature.
9. **Audit product presence.** Every hero section should have a vehicle — video or photograph — as the primary visual. The chrome should feel like it's framing the car, not competing with it.

enhace the ui of the Forntend and the Android app ui according to this ui <!doctype html>
<html lang="en" data-theme="dark">
  <head>
    <meta charset="UTF-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
    />
    <title>Portable AI - Fast Chat</title>
    <!-- Vendor assets (downloaded offline by platform installer) -->
    <link rel="stylesheet" href="./vendor/fa-all.min.css" />
    <link rel="stylesheet" href="./vendor/highlight-github-dark.min.css" />
    <script src="./vendor/marked.min.js"></script>
    <script src="./vendor/highlight.min.js"></script>
    <style>
      /* ── Local fonts (installed by platform installer) ─────── */
      @font-face {
        font-family: 'Inter';
        src: url('./vendor/Inter-Regular.woff2') format('woff2');
        font-weight: 400;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Inter';
        src: url('./vendor/Inter-Medium.woff2') format('woff2');
        font-weight: 500;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Inter';
        src: url('./vendor/Inter-SemiBold.woff2') format('woff2');
        font-weight: 600;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'Inter';
        src: url('./vendor/Inter-Bold.woff2') format('woff2');
        font-weight: 700;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'JetBrains Mono';
        src: url('./vendor/JetBrainsMono-Regular.woff2') format('woff2');
        font-weight: 400;
        font-style: normal;
        font-display: swap;
      }
      @font-face {
        font-family: 'JetBrains Mono';
        src: url('./vendor/JetBrainsMono-Medium.woff2') format('woff2');
        font-weight: 500;
        font-style: normal;
        font-display: swap;
      }
      /* Override Font Awesome font paths to use local vendor files */
      @font-face {
        font-family: 'Font Awesome 6 Free';
        font-style: normal;
        font-weight: 900;
        font-display: block;
        src: url('./vendor/fa-solid-900.woff2') format('woff2');
      }
      @font-face {
        font-family: 'Font Awesome 6 Free';
        font-style: normal;
        font-weight: 400;
        font-display: block;
        src: url('./vendor/fa-regular-400.woff2') format('woff2');
      }
      /* Tailwind responsive utilities (minimal subset used by this file) */
      .hidden {
        display: none !important;
      }
      @media (min-width: 640px) {
        .sm\:inline {
          display: inline !important;
        }
      }
      @media (min-width: 768px) {
        .md\:flex {
          display: flex !important;
        }
        .md\:hidden {
          display: none !important;
        }
      }
    </style>

    <style>
      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           DESIGN TOKENS ÔÇö Gemini/ChatGPT hybrid palette
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      :root {
        --font: 'Inter', sans-serif;
        --sb-w: 260px;
        --spd: 0.2s;
        --radius: 12px;
        --radius-lg: 18px;
        --radius-pill: 999px;
      }

      [data-theme='dark'] {
        /* Backgrounds ÔÇö ChatGPT layering */
        --bg: #212121;
        --bg2: #171717;
        --bg3: #2a2a2a;
        --bg-u: #2f2f2f;
        --bg-inp: #2f2f2f;

        /* Hovers */
        --bgh: rgba(255, 255, 255, 0.07);
        --bga: rgba(255, 255, 255, 0.1);

        /* Text */
        --t1: #ececec;
        --t2: #a0a0a0;
        --t3: #6b6b6b;

        /* Borders */
        --bd: #3a3a3a;
        --bd2: #555555;

        /* Code & misc */
        --code: #1a1a1a;
        --scr: #555;
        --shd: rgba(0, 0, 0, 0.5);
        --ov: rgba(0, 0, 0, 0.6);
        --tst: #3a3a3a;

        /* Brand gradient ÔÇö Gemini palette */
        --grad1: #4285f4;
        --grad2: #9b72cb;
        --grad3: #d96570;

        /* Status */
        --green: #6dd58c;
        --red: #f28b82;
        --orange: #fdb039;
      }

      [data-theme='light'] {
        --bg: #ffffff;
        --bg2: #f4f4f4;
        --bg3: #ebebeb;
        --bg-u: #f0f0f0;
        --bg-inp: #f4f4f4;

        --bgh: rgba(0, 0, 0, 0.05);
        --bga: rgba(0, 0, 0, 0.08);

        --t1: #1a1a1a;
        --t2: #5f5f5f;
        --t3: #9a9a9a;

        --bd: #e0e0e0;
        --bd2: #c0c0c0;

        --code: #f5f5f5;
        --scr: #c0c0c0;
        --shd: rgba(0, 0, 0, 0.08);
        --ov: rgba(0, 0, 0, 0.3);
        --tst: #2a2a2a;

        --grad1: #4285f4;
        --grad2: #9b72cb;
        --grad3: #d96570;

        --green: #1e8e3e;
        --red: #d93025;
        --orange: #f29900;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           RESET + BASE
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      *,
      *::before,
      *::after {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }
      html,
      body {
        height: 100%;
        overflow: hidden;
        font-family: var(--font);
        background: var(--bg);
        color: var(--t1);
        font-size: 15px;
        line-height: 1.6;
        transition:
          background var(--spd),
          color var(--spd);
        -webkit-font-smoothing: antialiased;
      }
      ::-webkit-scrollbar {
        width: 4px;
      }
      ::-webkit-scrollbar-track {
        background: transparent;
      }
      ::-webkit-scrollbar-thumb {
        background: var(--scr);
        border-radius: 4px;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           LAYOUT SHELL
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #app {
        display: flex;
        height: 100vh;
        width: 100vw;
        overflow: hidden;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           SIDEBAR ÔÇö ChatGPT flavour
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #sidebar {
        width: var(--sb-w);
        min-width: var(--sb-w);
        background: var(--bg2);
        border-right: 1px solid var(--bd);
        display: flex;
        flex-direction: column;
        transition:
          transform var(--spd) ease,
          min-width var(--spd) ease,
          width var(--spd) ease;
        z-index: 50;
        overflow: hidden;
      }
      #sidebar.off {
        transform: translateX(-100%);
        min-width: 0;
        width: 0;
        pointer-events: none;
      }

      /* Sidebar header */
      .sb-hd {
        padding: 16px 12px 12px;
        display: flex;
        flex-direction: column;
        gap: 8px;
      }
      .sb-brand {
        display: flex;
        align-items: center;
        gap: 9px;
        padding: 0 6px 8px;
      }
      .sb-brand-icon {
        width: 28px;
        height: 28px;
        background: linear-gradient(
          135deg,
          var(--grad1),
          var(--grad2),
          var(--grad3)
        );
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
      }
      .sb-brand-icon svg {
        width: 14px;
        height: 14px;
      }
      .sb-brand-name {
        font-size: 15px;
        font-weight: 600;
        color: var(--t1);
        letter-spacing: -0.3px;
      }

      /* New chat button */
      .nc-btn {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
        padding: 10px 16px;
        border-radius: var(--radius-pill);
        border: 1px solid var(--bd);
        background: transparent;
        color: var(--t2);
        font-family: var(--font);
        font-size: 13.5px;
        font-weight: 500;
        cursor: pointer;
        transition:
          background var(--spd),
          border-color var(--spd),
          color var(--spd);
      }
      .nc-btn:hover {
        background: var(--bgh);
        border-color: var(--bd2);
        color: var(--t1);
      }
      .nc-btn i {
        font-size: 13px;
      }

      /* Conversation list */
      .sb-section-label {
        font-size: 11px;
        font-weight: 600;
        color: var(--t3);
        text-transform: uppercase;
        letter-spacing: 0.06em;
        padding: 10px 14px 4px;
      }
      .sb-list {
        flex: 1;
        overflow-y: auto;
        padding: 4px 8px;
      }
      .cv {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 9px 12px;
        border-radius: 10px;
        cursor: pointer;
        transition: background 0.13s;
        margin-bottom: 1px;
        position: relative;
      }
      .cv:hover {
        background: var(--bgh);
      }
      .cv.act {
        background: var(--bga);
      }
      .cv .ci {
        font-size: 13px;
        color: var(--t3);
        flex-shrink: 0;
      }
      .cv .ct {
        flex: 1;
        font-size: 13px;
        color: var(--t2);
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      .cv.act .ct {
        color: var(--t1);
      }
      .cv .cd {
        opacity: 0;
        background: none;
        border: none;
        color: var(--t3);
        cursor: pointer;
        padding: 4px 5px;
        border-radius: 6px;
        transition:
          opacity 0.15s,
          color 0.15s;
        font-size: 12px;
        flex-shrink: 0;
      }
      .cv:hover .cd {
        opacity: 1;
      }
      .cv .cd:hover {
        color: var(--red);
      }

      /* Sidebar footer */
      .sb-ft {
        padding: 10px 12px;
        border-top: 1px solid var(--bd);
        display: flex;
        align-items: center;
        gap: 4px;
      }
      .sf-btn {
        background: none;
        border: none;
        color: var(--t3);
        cursor: pointer;
        padding: 8px;
        border-radius: 8px;
        transition:
          background 0.15s,
          color 0.15s;
        font-size: 15px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .sf-btn:hover {
        background: var(--bgh);
        color: var(--t1);
      }
      .sf-sp {
        flex: 1;
      }

      /* Overlay */
      #overlay {
        display: none;
        position: fixed;
        inset: 0;
        background: var(--ov);
        z-index: 45;
      }
      #overlay.on {
        display: block;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           MAIN PANEL
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #main {
        flex: 1;
        display: flex;
        flex-direction: column;
        min-width: 0;
        position: relative;
        background: var(--bg);
      }

      /* ÔöÇÔöÇ Topbar ÔöÇÔöÇ */
      #topbar {
        display: flex;
        align-items: center;
        padding: 10px 16px;
        gap: 10px;
        flex-shrink: 0;
        z-index: 10;
        min-height: 56px;
      }
      .tb-btn {
        background: none;
        border: none;
        color: var(--t3);
        cursor: pointer;
        padding: 8px;
        border-radius: 8px;
        transition:
          background 0.15s,
          color 0.15s;
        font-size: 17px;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .tb-btn:hover {
        background: var(--bgh);
        color: var(--t1);
      }
      .tb-title {
        font-size: 14.5px;
        font-weight: 500;
        color: var(--t2);
        user-select: none;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        max-width: 300px;
      }
      .tb-sp {
        flex: 1;
      }

      /* System Prompt button in topbar */
      #sys-prompt-btn {
        display: flex;
        align-items: center;
        gap: 6px;
        background: transparent;
        border: 1px solid var(--bd);
        border-radius: var(--radius-pill);
        padding: 6px 14px;
        font-size: 12.5px;
        color: var(--t2);
        cursor: pointer;
        transition:
          background 0.15s,
          border-color 0.15s,
          color 0.15s;
        font-family: var(--font);
        font-weight: 500;
      }
      #sys-prompt-btn:hover {
        background: var(--bgh);
        border-color: var(--bd2);
        color: var(--t1);
      }
      #sys-prompt-btn.has-global {
        color: var(--grad1);
        border-color: rgba(66, 133, 244, 0.35);
      }

      /* HW Stats bar */
      .hw-bar {
        display: flex;
        align-items: center;
        gap: 14px;
        background: var(--bg3);
        padding: 5px 12px;
        border-radius: var(--radius-pill);
        border: 1px solid var(--bd);
      }
      .hw-stat {
        display: flex;
        align-items: center;
        gap: 5px;
        font-size: 11px;
        color: var(--t3);
        font-weight: 500;
      }
      .hw-mini-track {
        width: 28px;
        height: 3px;
        background: var(--bd);
        border-radius: 2px;
        overflow: hidden;
      }
      .hw-mini-bar {
        height: 100%;
        border-radius: 2px;
        transform: scaleX(0);
        transform-origin: left;
        transition: transform 0.6s ease;
      }
      .hw-mini-bar.cpu {
        background: var(--grad1);
      }
      .hw-mini-bar.ram {
        background: var(--grad2);
      }
      .hw-pct {
        min-width: 28px;
        font-size: 11px;
        font-weight: 600;
      }
      .hw-pct.warn {
        color: var(--orange);
      }
      .hw-pct.danger {
        color: var(--red);
      }

    /* System Prompt Panel */
    #sys-panel {
      display: none;
      background: var(--bg2);
      padding: 14px 20px;
      border-bottom: 1px solid var(--bd);
      flex-direction: column;
      gap: 12px;
      animation: fadeSlideDown 0.2s ease;
    }

    #sys-panel.open {
      display: flex;
    }

    @keyframes fadeSlideDown {
      from {
        opacity: 0;
        transform: translateY(-6px);
      }

      to {
        opacity: 1;
        transform: translateY(0);
      }
    }

    .sys-row {
      display: flex;
      flex-direction: column;
      gap: 7px;
    }

    .sys-row label {
      font-size: 12.5px;
      font-weight: 600;
      color: var(--t2);
      letter-spacing: 0.01em;
    }

    #sys-ta {
      flex: 1;
      background: var(--bg);
      border: 1px solid var(--bd);
      color: var(--t1);
      font-size: 13.5px;
      border-radius: var(--radius);
      padding: 10px 14px;
      resize: none;
      height: 64px;
      font-family: var(--font);
      outline: none;
      transition:
        border-color 0.2s,
        box-shadow 0.2s;
    }

    #sys-ta:focus {
      border-color: var(--bd2);
      box-shadow: 0 0 0 3px rgba(66, 133, 244, 0.12);
    }

    .sys-actions {
      display: flex;
      align-items: center;
      gap: 8px;
    }

    .sys-divider {
      border: 0;
      border-top: 1px solid var(--bd);
      margin: 2px 0;
      opacity: 0.9;
    }

    .sys-subrow {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      gap: 10px;
    }

    .sys-select {
      background: var(--bg);
      border: 1px solid var(--bd);
      color: var(--t1);
      border-radius: var(--radius);
      font-size: 12.5px;
      padding: 7px 10px;
      min-width: 180px;
      font-family: var(--font);
      outline: none;
    }

    .sys-select:focus {
      border-color: var(--bd2);
      box-shadow: 0 0 0 3px rgba(66, 133, 244, 0.12);
    }

    .sys-hint {
      font-size: 11px;
      color: var(--t3);
    }

    .sys-btn {
      padding: 6px 16px;
      border-radius: var(--radius-pill);
      font-size: 12px;
      font-weight: 500;
      cursor: pointer;
      transition: background 0.15s;
      border: none;
      font-family: var(--font);
    }

    .sys-btn.primary {
      background: rgba(66, 133, 244, 0.15);
      color: #4285f4;
    }

    .sys-btn.primary:hover {
      background: rgba(66, 133, 244, 0.25);
    }

    .sys-btn.secondary {
      background: transparent;
      border: 1px solid var(--bd);
      color: var(--t2);
    }

    .sys-btn.secondary:hover {
      background: var(--bgh);
      color: var(--t1);
    }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           CHAT AREA
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #chat {
        flex: 1;
        overflow-y: auto;
        scroll-behavior: smooth;
        display: flex;
        flex-direction: column;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           WELCOME SCREEN ÔÇö Gemini style
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #welcome {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 40px 24px 20px;
        gap: 0;
        animation: welcomeFade 0.55s ease;
      }
      @keyframes welcomeFade {
        from {
          opacity: 0;
          transform: translateY(20px);
        }
        to {
          opacity: 1;
          transform: translateY(0);
        }
      }

      /* Gemini-style animated logo mark */
      .wl-wrap {
        width: 60px;
        height: 60px;
        margin-bottom: 18px;
        position: relative;
      }
      .wl-ring {
        position: absolute;
        inset: 0;
        border-radius: 50%;
        border: 2px solid transparent;
        background:
          linear-gradient(var(--bg), var(--bg)) padding-box,
          linear-gradient(135deg, var(--grad1), var(--grad2), var(--grad3))
            border-box;
        animation: ringPulse 3s ease-in-out infinite;
      }
      .wl-inner {
        position: absolute;
        inset: 8px;
        border-radius: 50%;
        background: linear-gradient(
          135deg,
          var(--grad1),
          var(--grad2),
          var(--grad3)
        );
        display: flex;
        align-items: center;
        justify-content: center;
        animation: innerPulse 3s ease-in-out infinite;
      }
      .wl-inner svg {
        width: 20px;
        height: 20px;
      }
      @keyframes ringPulse {
        0%,
        100% {
          transform: scale(1);
          opacity: 0.5;
        }
        50% {
          transform: scale(1.12);
          opacity: 1;
        }
      }
      @keyframes innerPulse {
        0%,
        100% {
          transform: scale(1);
        }
        50% {
          transform: scale(1.05);
        }
      }

      /* Gradient "Hello" title */
      .wt {
        font-size: 38px;
        font-weight: 700;
        letter-spacing: -1px;
        margin-bottom: 8px;
        background: linear-gradient(
          90deg,
          var(--grad1) 0%,
          var(--grad2) 40%,
          var(--grad3) 70%,
          var(--t1) 90%
        );
        background-size: 300% 100%;
        -webkit-background-clip: text;
        background-clip: text;
        color: transparent;
        animation: shimmer 5s ease-in-out infinite;
      }
      @keyframes shimmer {
        0% {
          background-position: 100% 0;
        }
        100% {
          background-position: -100% 0;
        }
      }
      .ws {
        font-size: 16px;
        color: var(--t2);
        margin-bottom: 36px;
        font-weight: 400;
      }

      /* Suggestion chips ÔÇö Gemini style */
      .sg {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 10px;
        max-width: 600px;
        width: 100%;
      }
      .sc {
        display: flex;
        align-items: center;
        gap: 12px;
        padding: 14px 16px;
        border-radius: 14px;
        border: 1px solid var(--bd);
        background: var(--bg2);
        cursor: pointer;
        transition:
          background 0.17s,
          border-color 0.17s,
          transform 0.12s,
          box-shadow 0.17s;
      }
      .sc:hover {
        background: var(--bgh);
        border-color: var(--bd2);
        transform: translateY(-1px);
        box-shadow: 0 4px 16px var(--shd);
      }
      .sc .si {
        width: 32px;
        height: 32px;
        border-radius: 9px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 13px;
        flex-shrink: 0;
      }
      .sc .st {
        font-size: 13px;
        color: var(--t2);
        line-height: 1.45;
      }
      .ib {
        background: rgba(66, 133, 244, 0.12);
        color: #4285f4;
      }
      .ip {
        background: rgba(155, 114, 203, 0.12);
        color: #9b72cb;
      }
      .it {
        background: rgba(52, 168, 124, 0.12);
        color: #34a87c;
      }
      .io {
        background: rgba(234, 134, 59, 0.12);
        color: #ea863b;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           MESSAGES
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #msgs {
        max-width: 780px;
        width: 100%;
        margin: 0 auto;
        padding: 24px 24px 16px;
        display: none;
        flex-direction: column;
      }
      #msgs.on {
        display: flex;
      }

      /* Message row */
      .mr {
        display: flex;
        gap: 14px;
        padding: 16px 0 6px;
        animation: msgIn 0.25s ease;
      }
      @keyframes msgIn {
        from {
          opacity: 0;
          transform: translateY(8px);
        }
        to {
          opacity: 1;
          transform: translateY(0);
        }
      }
      .mr.usr {
        justify-content: flex-end;
      }

      /* Avatar */
      .ma {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        flex-shrink: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 2px;
      }
      .ma.ai {
        background: linear-gradient(
          135deg,
          var(--grad1),
          var(--grad2),
          var(--grad3)
        );
      }
      .ma.ai svg {
        width: 16px;
        height: 16px;
      }
      .ma.u {
        background: var(--bg3);
        border: 1px solid var(--bd);
        font-size: 11px;
        font-weight: 700;
        color: var(--t2);
      }

      .mc {
        flex: 1;
        min-width: 0;
        max-width: 700px;
      }
      .usr .mc {
        display: flex;
        flex-direction: column;
        align-items: end;
        max-width: 82%;
      }

      /* User bubble ÔÇö ChatGPT style */
      .ub {
        background: var(--bg-u);
        border-radius: 22px;
        border-top-right-radius: 5px;
        padding: 12px 18px;
        border: 1px solid var(--bd);
        display: inline-block;
      }

      /* Text content */
      .mt {
        font-size: 15px;
        line-height: 1.72;
        word-wrap: break-word;
        color: var(--t1);
      }

      .mc > .mt {
        padding-top: 0.3rem;
      }

      /* Markdown styles */
      .mt p {
        margin-bottom: 12px;
      }
      .mt p:last-child {
        margin-bottom: 0;
      }
      .mt h1,
      .mt h2,
      .mt h3 {
        font-weight: 600;
        margin: 20px 0 10px;
        color: var(--t1);
      }
      .mt h1 {
        font-size: 1.35em;
      }
      .mt h2 {
        font-size: 1.18em;
      }
      .mt h3 {
        font-size: 1.06em;
      }
      .mt strong {
        font-weight: 600;
      }
      .mt a {
        color: var(--grad1);
        text-decoration: none;
      }
      .mt a:hover {
        text-decoration: underline;
      }
      .mt ul,
      .mt ol {
        margin: 12px 0;
        padding-left: 24px;
      }
      .mt li {
        margin-bottom: 5px;
      }
      .mt hr {
        border: none;
        border-top: 1px solid var(--bd);
        margin: 20px 0;
      }
      .mt table {
        width: 100%;
        border-collapse: collapse;
        margin: 16px 0;
        font-size: 14px;
        border-radius: 10px;
        overflow: hidden;
        border: 1px solid var(--bd);
      }
      .mt th,
      .mt td {
        padding: 8px 14px;
        border: 1px solid var(--bd);
        text-align: left;
      }
      .mt th {
        background: var(--bg2);
        font-weight: 600;
      }

      /* Inline code */
      .mt code:not(.hljs) {
        background: var(--bg3);
        padding: 2px 6px;
        border-radius: 6px;
        font-family: 'JetBrains Mono', 'SF Mono', monospace;
        font-size: 0.88em;
        border: 1px solid var(--bd);
      }

      /* Code blocks */
      .cblk {
        margin: 16px 0;
        border-radius: 12px;
        overflow: hidden;
        border: 1px solid var(--bd);
        background: var(--code);
      }
      .cbh {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 9px 14px;
        background: var(--bg2);
        border-bottom: 1px solid var(--bd);
        font-size: 12px;
        color: var(--t3);
      }
      .cbc-btn {
        background: none;
        border: none;
        color: var(--t3);
        cursor: pointer;
        font-size: 12px;
        padding: 3px 8px;
        border-radius: 6px;
        transition:
          background 0.15s,
          color 0.15s;
        display: flex;
        align-items: center;
        gap: 5px;
        font-family: var(--font);
      }
      .cbc-btn:hover {
        background: var(--bgh);
        color: var(--t1);
      }
      .cblk pre {
        padding: 16px;
        overflow-x: auto;
        margin: 0;
        background: transparent !important;
      }
      .cblk pre code {
        font-family: 'JetBrains Mono', 'SF Mono', monospace;
        font-size: 13px;
        line-height: 1.65;
        background: transparent !important;
        padding: 0;
      }

      /* Attachment previews in messages */
      .msg-img {
        max-width: 280px;
        border-radius: 12px;
        display: block;
        border: 1px solid var(--bd);
      }
      .msg-pdf-pill {
        display: inline-flex;
        align-items: center;
        gap: 6px;
        background: var(--bg3);
        padding: 6px 12px;
        border-radius: 16px;
        font-size: 12px;
        color: var(--t2);
        border: 1px solid var(--bd);
      }
      .usr-attach {
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        gap: 6px;
        margin-top: 6px;
      }

      /* Message actions (copy/like/dislike) */
      .mact {
        display: flex;
        gap: 1px;
        margin-top: 8px;
        opacity: 0;
        transition: opacity 0.18s;
      }
      .mr:hover .mact {
        opacity: 1;
      }
      .mab {
        background: none;
        border: none;
        color: var(--t3);
        cursor: pointer;
        padding: 6px 8px;
        border-radius: 8px;
        font-size: 13px;
        transition:
          background 0.15s,
          color 0.15s;
        display: flex;
        align-items: center;
        gap: 4px;
      }
      .mab:hover {
        background: var(--bgh);
        color: var(--t1);
      }
      .mab.lk {
        color: var(--grad1);
      }
      .mab.dlk {
        color: var(--red);
      }

      .msg-meta {
        display: flex;
        align-items: center;
        gap: 10px;
        min-height: 20px;
        margin-top: 8px;
        color: var(--t3);
        font-size: 12px;
        line-height: 1.35;
      }
      .msg-meta.live {
        color: var(--t2);
      }
      .msg-meta .dot {
        width: 4px;
        height: 4px;
        border-radius: 50%;
        background: currentColor;
        opacity: 0.55;
      }

      /* Typing dots */
      .think {
        display: inline-flex;
        align-items: center;
        gap: 10px;
        color: var(--t2);
        font-size: 14px;
        line-height: 1;
        padding: 9px 0;
      }
      .typ {
        display: inline-flex;
        align-items: center;
        gap: 5px;
      }
      .typ span {
        width: 7px;
        height: 7px;
        background: var(--t3);
        border-radius: 50%;
        animation: typBounce 1.4s infinite ease-in-out;
      }
      .typ span:nth-child(2) {
        animation-delay: 0.16s;
      }
      .typ span:nth-child(3) {
        animation-delay: 0.32s;
      }
      @keyframes typBounce {
        0%,
        60%,
        100% {
          transform: translateY(0);
          opacity: 0.4;
        }
        30% {
          transform: translateY(-6px);
          opacity: 1;
        }
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           INPUT AREA ÔÇö Gemini/ChatGPT style
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #inp-area {
        padding: 8px 20px 18px;
        flex-shrink: 0;
        display: flex;
        flex-direction: column;
        align-items: center;
      }

      /* Vision warning */
      #vision-warn {
        display: none;
        align-items: center;
        gap: 8px;
        max-width: 740px;
        width: 100%;
        margin-bottom: 8px;
        padding: 10px 16px;
        background: rgba(253, 176, 57, 0.08);
        border-radius: 10px;
        font-size: 13px;
        color: var(--orange);
        border: 1px solid rgba(253, 176, 57, 0.2);
      }
      #vision-warn.on {
        display: flex;
      }

      /* File/image preview bar */
      .fbar {
        max-width: 740px;
        width: 100%;
        display: none;
        gap: 10px;
        flex-wrap: wrap;
      }
      .fbar.on {
        display: flex;
        padding: 10px 14px 4px;
      }
      .img-preview {
        position: relative;
        width: 60px;
        height: 60px;
        border-radius: 10px;
        overflow: hidden;
        border: 1px solid var(--bd);
      }
      .img-preview img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
      .pdf-preview {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 8px 14px;
        background: var(--bg3);
        border-radius: 12px;
        border: 1px solid var(--bd);
      }
      .pdf-preview .p-info {
        display: flex;
        flex-direction: column;
        max-width: 130px;
      }
      .pdf-preview strong {
        font-size: 12px;
        color: var(--t1);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      .pdf-preview span {
        font-size: 10px;
        color: var(--t3);
      }
      .f-rm {
        position: absolute;
        top: 2px;
        right: 2px;
        background: rgba(0, 0, 0, 0.65);
        color: #fff;
        border: none;
        border-radius: 50%;
        width: 20px;
        height: 20px;
        font-size: 10px;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .pdf-preview .f-rm {
        position: relative;
        top: 0;
        right: 0;
        background: transparent;
        color: var(--t3);
      }
      .pdf-preview .f-rm:hover {
        color: var(--red);
      }

      /* The main input wrapper ÔÇö pill/rounded-rectangle */
      .iw {
        max-width: 740px;
        width: 100%;
        display: flex;
        flex-direction: column;
        background: var(--bg-inp);
        border: 1px solid var(--bd);
        border-radius: 22px;
        transition:
          border-color 0.2s,
          box-shadow 0.2s;
        overflow: visible;
        position: relative;
      }
      .iw:focus-within,
      .iw.ht {
        border-color: var(--bd2);
        box-shadow: 0 2px 20px var(--shd);
      }

      /* ÔöÇÔöÇ Model Selector Row (INSIDE input, at top) ÔöÇÔöÇ */
      .model-row {
        display: flex;
        align-items: center;
        padding: 10px 14px 0;
        gap: 6px;
      }

      .model-dd {
        position: relative;
      }

      .model-btn {
        display: flex;
        align-items: center;
        gap: 6px;
        padding: 5px 11px 5px 8px;
        border-radius: var(--radius-pill);
        border: 1px solid var(--bd);
        background: var(--bg3);
        color: var(--t2);
        font-family: var(--font);
        font-size: 12px;
        font-weight: 500;
        cursor: pointer;
        transition:
          background 0.15s,
          border-color 0.15s,
          color 0.15s;
      }
      .model-btn:hover {
        background: var(--bgh);
        border-color: var(--bd2);
        color: var(--t1);
      }
      .model-btn svg {
        width: 13px;
        height: 13px;
        flex-shrink: 0;
      }
      .model-btn .chev {
        font-size: 9px;
        color: var(--t3);
        margin-left: 2px;
        transition: transform 0.2s;
      }
      .model-btn.open .chev {
        transform: rotate(180deg);
      }

      /* Model dropdown menu */
      .model-menu {
        position: absolute;
        bottom: calc(100% + 8px);
        left: 0;
        background: var(--bg3);
        border: 1px solid var(--bd);
        border-radius: 16px;
        padding: 6px;
        min-width: 250px;
        max-height: 320px;
        overflow-y: auto;
        z-index: 200;
        box-shadow: 0 12px 36px var(--shd);
        display: none;
        animation: menuPop 0.15s ease;
      }
      .model-menu.on {
        display: block;
      }
      @keyframes menuPop {
        from {
          opacity: 0;
          transform: translateY(6px);
        }
        to {
          opacity: 1;
          transform: translateY(0);
        }
      }
      .mm-opt {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 10px 12px;
        border-radius: 10px;
        cursor: pointer;
        transition: background 0.13s;
      }
      .mm-opt:hover {
        background: var(--bgh);
      }
      .mm-opt.sel {
        background: var(--bga);
      }
      .mmo-icon {
        width: 28px;
        height: 28px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 12px;
        flex-shrink: 0;
        background: linear-gradient(
          135deg,
          var(--grad1),
          var(--grad2),
          var(--grad3)
        );
        color: #fff;
      }
      .mmo-info {
        flex: 1;
        min-width: 0;
      }
      .mmo-name {
        font-size: 13px;
        font-weight: 500;
        color: var(--t1);
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
      .mmo-desc {
        font-size: 11px;
        color: var(--t3);
        margin-top: 1px;
      }
      .mmo-chk {
        color: var(--grad1);
        font-size: 12px;
        opacity: 0;
      }
      .mm-opt.sel .mmo-chk {
        opacity: 1;
      }

      /* Temperature control */
      .temp-ctrl {
        margin-left: auto;
        display: flex;
        align-items: center;
        gap: 5px;
      }
      .temp-lbl {
        font-size: 11px;
        color: var(--t3);
        font-weight: 500;
      }
      #temp-input {
        background: var(--bg3);
        border: 1px solid var(--bd);
        color: var(--t1);
        font-size: 12px;
        padding: 3px 6px;
        border-radius: 6px;
        width: 54px;
        text-align: center;
        font-family: var(--font);
      }
      #temp-input:focus {
        outline: none;
        border-color: var(--bd2);
      }

      /* ÔöÇÔöÇ Text row (textarea + action buttons) ÔöÇÔöÇ */
      .text-row {
        display: flex;
        align-items: flex-end;
        padding: 6px 8px 8px 16px;
      }
      #msg-inp {
        flex: 1;
        background: transparent;
        border: none;
        outline: none;
        font-family: var(--font);
        font-size: 15px;
        color: var(--t1);
        resize: none;
        max-height: 200px;
        line-height: 1.55;
        padding: 6px 4px;
      }
      #msg-inp::placeholder {
        color: var(--t3);
      }

      /* Action buttons row */
      .ia {
        display: flex;
        align-items: center;
        gap: 3px;
        flex-shrink: 0;
      }

      /* Icon button base */
      .ib2 {
        background: none;
        border: none;
        color: var(--t3);
        cursor: pointer;
        padding: 8px;
        border-radius: 50%;
        font-size: 15px;
        transition:
          background 0.15s,
          color 0.15s;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .ib2:hover {
        background: var(--bgh);
        color: var(--t1);
      }

      /* Send button */
      .sbtn {
        width: 36px;
        height: 36px;
        border-radius: 50%;
        background: var(--bd);
        color: var(--bg);
        transition:
          background 0.2s,
          transform 0.12s;
        font-size: 14px;
      }
      .sbtn.on {
        background: var(--t1);
        cursor: pointer;
      }
      .sbtn.on:hover {
        transform: scale(1.06);
      }
      .sbtn.on:active {
        transform: scale(0.94);
      }

      /* Stop streaming button */
      .stbtn {
        width: 36px;
        height: 36px;
        border-radius: 50%;
        background: var(--t1);
        color: var(--bg);
        cursor: pointer;
        font-size: 13px;
        transition: background 0.2s;
        border: none;
      }
      .stbtn:hover {
        opacity: 0.85;
      }

      /* Disclaimer */
      .disc {
        font-size: 11.5px;
        color: var(--t3);
        text-align: center;
        margin-top: 8px;
        max-width: 740px;
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           TOASTS
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      #toasts {
        position: fixed;
        bottom: 90px;
        left: 50%;
        transform: translateX(-50%);
        z-index: 300;
        display: flex;
        flex-direction: column;
        gap: 8px;
        align-items: center;
        pointer-events: none;
      }
      .toast {
        background: var(--tst);
        color: var(--t1);
        padding: 9px 20px;
        border-radius: 10px;
        font-size: 13px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.35);
        animation:
          toastIn 0.22s ease,
          toastOut 0.22s ease 2.2s forwards;
        white-space: nowrap;
        border: 1px solid var(--bd);
      }
      @keyframes toastIn {
        from {
          opacity: 0;
          transform: translateY(8px);
        }
        to {
          opacity: 1;
          transform: translateY(0);
        }
      }
      @keyframes toastOut {
        from {
          opacity: 1;
        }
        to {
          opacity: 0;
          transform: translateY(-6px);
        }
      }

      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
           RESPONSIVE
        ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */
      @media (max-width: 768px) {
        #sidebar {
          position: fixed;
          left: 0;
          top: 0;
          height: 100%;
        }
        #sidebar.off {
          transform: translateX(-100%);
        }
        .sg {
          grid-template-columns: 1fr;
        }
        .usr .mc {
          max-width: 88%;
        }
        .wt {
          font-size: 30px;
        }
        #inp-area {
          padding: 6px 12px 14px;
        }
        #msgs {
          padding: 16px 16px 12px;
        }
      }
      @media (prefers-reduced-motion: reduce) {
        *,
        *::before,
        *::after {
          animation-duration: 0.01ms !important;
          transition-duration: 0.01ms !important;
        }
      }

      /* Drag-and-drop overlay */
      #main.drag-over::after {
        content: 'Drop image, PDF or text file';
        position: absolute;
        inset: 0;
        background: rgba(66, 133, 244, 0.1);
        border: 2px dashed var(--grad1);
        border-radius: var(--radius-lg);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 16px;
        color: var(--grad1);
        font-weight: 500;
        pointer-events: none;
        z-index: 20;
      }
      #main {
        position: relative;
      }
    </style>
  </head>
  <body>
    <div id="app">
      <!-- ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ SIDEBAR ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ -->
      <aside id="sidebar" role="navigation" aria-label="Chat history">
        <div class="sb-hd">
          <div class="sb-brand">
            <div class="sb-brand-icon">
              <svg viewBox="0 0 24 24">
                <path
                  d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                  fill="white"
                />
              </svg>
            </div>
            <span class="sb-brand-name">Portable AI</span>
          </div>
          <button class="nc-btn" id="nc-btn" aria-label="New chat">
            <i class="fa-solid fa-plus"></i>
            <span>New chat</span>
          </button>
        </div>

        <div class="sb-list" id="cv-list"></div>

        <div class="sb-ft">
          <button
            class="sf-btn"
            id="th-sb"
            aria-label="Toggle theme"
            title="Toggle theme"
          >
            <i class="fa-solid fa-moon"></i>
          </button>
          <div class="sf-sp"></div>
          <button
            class="sf-btn"
            id="ca-btn"
            aria-label="Clear all chats"
            title="Clear all chats"
          >
            <i class="fa-solid fa-trash-can"></i>
          </button>
        </div>
      </aside>

      <div id="overlay"></div>

      <!-- ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ MAIN ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ -->
      <main id="main">
        <!-- Top Bar -->
        <header id="topbar">
          <button class="tb-btn" id="sb-tog" aria-label="Toggle sidebar">
            <i class="fa-solid fa-bars"></i>
          </button>

          <div class="tb-title" id="tb-title"></div>
          <div class="tb-sp"></div>

          <button id="sys-prompt-btn" title="System Instructions">
            <i class="fa-solid fa-sliders"></i>
            <span class="hidden sm:inline">Instructions</span>
          </button>

          <!-- HW Stats ÔÇö hidden on mobile -->
          <div class="hw-bar hidden md:flex" id="hw-bar">
            <div class="hw-stat">
              <span style="color: var(--t3); font-size: 10px">CPU</span>
              <div class="hw-mini-track">
                <div class="hw-mini-bar cpu" id="cpu-bar"></div>
              </div>
              <span class="hw-pct" id="cpu-pct">--%</span>
            </div>
            <div class="hw-stat">
              <span style="color: var(--t3); font-size: 10px">RAM</span>
              <div class="hw-mini-track">
                <div class="hw-mini-bar ram" id="ram-bar"></div>
              </div>
              <span class="hw-pct" id="ram-pct">--%</span>
            </div>
          </div>

          <button
            class="tb-btn md:hidden"
            id="th-top"
            aria-label="Toggle theme"
          >
            <i class="fa-solid fa-moon"></i>
          </button>
        </header>

      <!-- System Prompt Panel (slides down from topbar) -->
      <div id="sys-panel">
        <div class="sys-row">
          <label>System Instructions</label>
          <textarea id="sys-ta" placeholder="Add context or instructions for this conversation..."></textarea>
        </div>
        <div class="sys-actions">
          <button class="sys-btn primary" id="set-global-btn">
            Set as Default
          </button>
          <button class="sys-btn secondary" id="clear-global-btn">
            Clear
          </button>
        </div>
        <hr class="sys-divider" />
        <div class="sys-row">
          <label for="log-mode-select">Logging</label>
          <div class="sys-subrow">
            <select id="log-mode-select" class="sys-select">
              <option value="errors_only">Errors only (Recommended)</option>
              <option value="all">Everything (Verbose)</option>
            </select>
            <span class="sys-hint">Saved per device. Reduce disk writes by using errors only.</span>
          </div>
        </div>
      </div>

        <!-- Chat / Messages -->
        <div id="chat">
          <!-- Welcome screen -->
          <div id="welcome">
            <div class="wl-wrap">
              <div class="wl-ring"></div>
              <div class="wl-inner">
                <svg viewBox="0 0 24 24">
                  <path
                    d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                    fill="white"
                  />
                </svg>
              </div>
            </div>

            <div class="wt">Hello</div>
            <div class="ws">What can I help you with today?</div>

            <div class="sg">
              <div
                class="sc"
                data-prompt="Help me write a professional email to reschedule a meeting"
              >
                <div class="si ib"><i class="fa-solid fa-pen-fancy"></i></div>
                <div class="st">
                  Help me write a professional email to reschedule a meeting
                </div>
              </div>
              <div
                class="sc"
                data-prompt="Explain quantum computing in simple terms"
              >
                <div class="si ip"><i class="fa-solid fa-lightbulb"></i></div>
                <div class="st">Explain quantum computing in simple terms</div>
              </div>
              <div
                class="sc"
                data-prompt="Write a Python function to find prime numbers"
              >
                <div class="si it"><i class="fa-solid fa-code"></i></div>
                <div class="st">
                  Write a Python function to find prime numbers
                </div>
              </div>
              <div class="sc" data-prompt="Plan a 7-day trip to Tokyo, Japan">
                <div class="si io"><i class="fa-solid fa-plane"></i></div>
                <div class="st">Plan a 7-day trip to Tokyo, Japan</div>
              </div>
            </div>
          </div>

          <div id="msgs"></div>
        </div>

        <!-- Input Area -->
        <div id="inp-area">
          <!-- Vision warning -->
          <div id="vision-warn">
            <i class="fa-solid fa-triangle-exclamation"></i>
            <span
              ><strong id="warn-model"></strong> is text-only. It cannot process
              images. Try a vision model like <code>llava</code>.</span
            >
          </div>

          <!-- Main input wrapper -->
          <div class="iw" id="iw">
            <!-- File/image preview bar (inside the box) -->
            <div class="fbar" id="fbar"></div>

            <!-- Model selector row ÔÇö INSIDE the input box at top -->
            <div class="model-row">
              <div class="model-dd" id="model-dd">
                <button
                  class="model-btn"
                  id="model-btn"
                  aria-label="Select model"
                >
                  <svg viewBox="0 0 24 24">
                    <defs>
                      <linearGradient
                        id="mvg"
                        x1="0%"
                        y1="0%"
                        x2="100%"
                        y2="100%"
                      >
                        <stop offset="0%" stop-color="var(--grad1)" />
                        <stop offset="50%" stop-color="var(--grad2)" />
                        <stop offset="100%" stop-color="var(--grad3)" />
                      </linearGradient>
                    </defs>
                    <path
                      d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z"
                      fill="url(#mvg)"
                    />
                  </svg>
                  <span id="model-name">Loading...</span>
                  <i class="fa-solid fa-chevron-down chev"></i>
                </button>

                <!-- Model dropdown (opens upward) -->
                <div class="model-menu" id="model-menu">
                  <div
                    style="
                      padding: 16px;
                      font-size: 12px;
                      color: var(--t3);
                      text-align: center;
                    "
                  >
                    Connecting to Engine...
                  </div>
                </div>
              </div>
              <div class="temp-ctrl">
                <label class="temp-lbl">Temp</label>
                <input
                  type="number"
                  id="temp-input"
                  value="0.7"
                  min="0"
                  max="2"
                  step="0.1"
                />
              </div>
            </div>

            <!-- Textarea + send row -->
            <div class="text-row">
              <textarea
                id="msg-inp"
                placeholder="Ask anything..."
                rows="1"
                aria-label="Message input"
              ></textarea>
              <div class="ia">
                <button
                  class="ib2"
                  id="att-btn"
                  aria-label="Attach file"
                  title="Upload image or PDF"
                >
                  <i class="fa-solid fa-paperclip"></i>
                </button>
                <input
                  type="file"
                  id="f-inp"
                  hidden
                  multiple
                  accept="image/*,.pdf,application/pdf,.txt,.md"
                />
                <button
                  class="ib2 sbtn"
                  id="send-btn"
                  aria-label="Send message"
                  disabled
                >
                  <i class="fa-solid fa-arrow-up"></i>
                </button>
              </div>
            </div>
          </div>

          <p class="disc">
            Responses are generated locally. May contain inaccuracies.
          </p>
        </div>
      </main>
    </div>

    <div id="toasts"></div>

    <script>
      /* ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ
   Portable AI ÔÇö Fast Chat ┬À Core Logic (unchanged)
   ÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉÔòÉ */

      // ÔöÇÔöÇ Config ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      const IS_SERVED =
        location.protocol === 'http:' || location.protocol === 'https:';
      const OLLAMA = IS_SERVED ? '/ollama' : 'http://127.0.0.1:11434';
      const VISION_MODELS = [
        'llava',
        'moondream',
        'bakllava',
        'vision',
        'minicpm-v',
        'cogvlm',
        'qwen-vl',
        'phi-3-vision',
      ];

    const S = {
      convs: [],
      curId: null,
      theme: localStorage.getItem('g-theme') || 'dark',
      sbOpen: window.innerWidth > 768,
      streaming: false,
      abort: null,
      models: [],
      model: localStorage.getItem('g-model') || '',
      engineReady: false,
      globalSys: '',
      logMode: localStorage.getItem('logMode') || 'errors_only',

        // Attachments (multi-file)
        attachments: [],
      };

      // ÔöÇÔöÇ DOM ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      const $ = (s) => document.querySelector(s);
      const $$ = (s) => document.querySelectorAll(s);

      const D = {
        sb: $('#sidebar'),
        ov: $('#overlay'),
        cvList: $('#cv-list'),
        welcome: $('#welcome'),
        msgs: $('#msgs'),
        chat: $('#chat'),
        inp: $('#msg-inp'),
        iw: $('#iw'),
        send: $('#send-btn'),
        att: $('#att-btn'),
        fInp: $('#f-inp'),
        fBar: $('#fbar'),
        nc: $('#nc-btn'),
        sbTog: $('#sb-tog'),
        thTop: $('#th-top'),
        thSb: $('#th-sb'),
        ca: $('#ca-btn'),
        toasts: $('#toasts'),
        modelBtn: $('#model-btn'),
        modelMenu: $('#model-menu'),
        modelName: $('#model-name'),
        tbTitle: $('#tb-title'),
        modelDd: $('#model-dd'),

      // Extensions
      sysBtn: $('#sys-prompt-btn'),
      sysPanel: $('#sys-panel'),
      sysTa: $('#sys-ta'),
      logModeSel: $('#log-mode-select'),
      sysSet: $('#set-global-btn'),
      sysClr: $('#clear-global-btn'),
      warn: $('#vision-warn'),
      warnModel: $('#warn-model'),
    };

      // ÔöÇÔöÇ Init ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      async function init() {
        setupMarked();
        applyTheme(S.theme);
        if (!S.sbOpen) D.sb.classList.add('off');

        await loadGlobalPrompt();
        await fetchModels();
        await load();

        renderSB();
        renderChat();
        bind();

        if (S.convs.length > 0) switchConv(S.convs[0].id);

        pollHW();
        setInterval(pollHW, 5000);
        setInterval(fetchModels, 15000);
      }

      // ÔöÇÔöÇ Hardware Stats ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      async function pollHW() {
        if (!IS_SERVED) return;
        try {
          const r = await fetch('/api/stats');
          if (!r.ok) return;
          const d = await r.json();
          if (d.ram_percent === -1) return;
          setBar('cpu', d.cpu_percent);
          setBar('ram', d.ram_percent);
        } catch {}
      }
      function setBar(type, pct) {
        const bar = $(`#${type}-bar`);
        const lbl = $(`#${type}-pct`);
        if (!bar) return;
        bar.style.transform = `scaleX(${Math.max(0, Math.min(100, pct)) / 100})`;
        lbl.textContent = pct + '%';
        lbl.className =
          'hw-pct' + (pct >= 90 ? ' danger' : pct >= 70 ? ' warn' : '');
      }

    // Global Prompt
    async function loadGlobalPrompt() {
      if (IS_SERVED) {
        try {
          const r = await fetch('/api/settings');
          if (r.ok) {
            const s = await r.json();
            S.globalSys = s.globalSystemPrompt || '';
            S.logMode = s.logMode === 'all' ? 'all' : 'errors_only';
          }
        } catch { }
      } else {
        S.globalSys = localStorage.getItem('globalSystemPrompt') || '';
        S.logMode = localStorage.getItem('logMode') || 'errors_only';
      }
      updateSysUI();
    }
    async function saveGlobalPrompt() {
      S.globalSys = D.sysTa.value.trim();
      S.logMode = D.logModeSel.value === 'all' ? 'all' : 'errors_only';
      if (IS_SERVED) {
        try {
          await fetch('/api/settings', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              globalSystemPrompt: S.globalSys,
              logMode: S.logMode,
            }),
          });
        } catch { }
      } else {
        localStorage.setItem('globalSystemPrompt', S.globalSys);
        localStorage.setItem('logMode', S.logMode);
      }
      updateSysUI();
      toast('Default prompt saved');
    }
    async function clearGlobalPrompt() {
      S.globalSys = '';
      D.sysTa.value = '';
      S.logMode = D.logModeSel.value === 'all' ? 'all' : 'errors_only';
      if (IS_SERVED) {
        try {
          await fetch('/api/settings', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              globalSystemPrompt: '',
              logMode: S.logMode,
            }),
          });
        } catch { }
      } else {
        localStorage.removeItem('globalSystemPrompt');
        localStorage.setItem('logMode', S.logMode);
      }
      updateSysUI();
      toast('Default prompt cleared');
    }
    async function saveLogMode() {
      S.logMode = D.logModeSel.value === 'all' ? 'all' : 'errors_only';
      if (IS_SERVED) {
        try {
          await fetch('/api/settings', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ logMode: S.logMode }),
          });
        } catch { }
      } else {
        localStorage.setItem('logMode', S.logMode);
      }
      toast(
        S.logMode === 'all'
          ? 'Logging: everything'
          : 'Logging: errors only',
      );
    }
    function updateSysUI() {
      D.logModeSel.value = S.logMode === 'all' ? 'all' : 'errors_only';
      if (S.globalSys) D.sysBtn.classList.add('has-global');
      else D.sysBtn.classList.remove('has-global');
    }

      // ÔöÇÔöÇ Models ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      function isVision(name) {
        return VISION_MODELS.some((v) =>
          (name || '').toLowerCase().includes(v),
        );
      }

      async function fetchModels() {
        try {
          const r = await fetch(OLLAMA + '/api/tags');
          if (!r.ok) throw new Error();
          const d = await r.json();
          S.models = Array.isArray(d.models) ? d.models : [];
          S.engineReady = S.models.length > 0;
          renderModelMenu();
          if (
            S.models.length &&
            (!S.model || !S.models.find((m) => m.name === S.model))
          ) {
            applyModel(S.models[0].name);
          } else if (S.models.length && S.model) {
            applyModel(S.model);
          } else if (!S.models.length) {
            S.model = '';
            D.modelName.textContent = 'No models installed';
            D.modelMenu.innerHTML =
              '<div style="padding:16px;font-size:12px;color:var(--t3);text-align:center;">No models installed</div>';
          }
          updateSend();
        } catch {
          S.models = [];
          S.model = '';
          S.engineReady = false;
          D.modelName.textContent = 'Engine Offline';
          D.modelMenu.innerHTML =
            '<div style="padding:16px;font-size:12px;color:var(--red);text-align:center;">Engine Offline</div>';
          updateSend();
        }
      }

      function renderModelMenu() {
        if (!S.models.length) {
          D.modelMenu.innerHTML =
            '<div style="padding:16px;font-size:12px;color:var(--t3);text-align:center;">No models installed</div>';
          return;
        }
        D.modelMenu.innerHTML = S.models
          .map(
            (m) => `
        <div class="mm-opt ${m.name === S.model ? 'sel' : ''}" data-model="${m.name}">
            <div class="mmo-icon"><i class="fa-solid ${isVision(m.name) ? 'fa-eye' : 'fa-microchip'}"></i></div>
            <div class="mmo-info">
                <div class="mmo-name">${esc(m.name)}</div>
                <div class="mmo-desc">${(m.size / 1e9).toFixed(1)} GB</div>
            </div>
            <i class="fa-solid fa-check mmo-chk"></i>
        </div>`,
          )
          .join('');

        $$('.mm-opt').forEach((opt) =>
          opt.addEventListener('click', (e) => {
            e.stopPropagation();
            const model = opt.dataset.model;
            applyModel(model);
            const conv = getConv();
            if (conv) {
              conv.model = model;
              save();
            }
            toggleModelMenu(false);
          }),
        );
      }

      function applyModel(model) {
        S.model = model;
        localStorage.setItem('g-model', model);
        D.modelName.textContent = model;
        $$('.mm-opt').forEach((opt) =>
          opt.classList.toggle('sel', opt.dataset.model === model),
        );
        checkVisionWarn();
      }

      function toggleModelMenu(show) {
        const isOpen = D.modelMenu.classList.contains('on');
        const shouldOpen = show !== undefined ? show : !isOpen;
        D.modelMenu.classList.toggle('on', shouldOpen);
        D.modelBtn.classList.toggle('open', shouldOpen);
      }

      // ÔöÇÔöÇ File Attachments ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      async function handleAttach(files) {
        const list = Array.from(files || []).filter(Boolean);
        if (!list.length) return;

        let added = 0;
        let rejected = 0;
        for (const file of list) {
          const ok = await handleSingleAttachment(file);
          if (ok) added++;
          else rejected++;
        }

        if (added) {
          renderAttachmentBar();
          checkVisionWarn();
          updateSend();
        }
        if (rejected) {
          toast(
            rejected === list.length
              ? 'No supported files selected'
              : `${rejected} file(s) skipped`,
          );
        }
      }

      async function handleSingleAttachment(file) {
        if (!file) return false;
        const name = file.name.toLowerCase();
        if (file.type.startsWith('image/')) return handleImg(file);
        if (file.type === 'application/pdf' || name.endsWith('.pdf'))
          return handlePdf(file);
        if (
          file.type === 'text/plain' ||
          name.endsWith('.md') ||
          name.endsWith('.txt')
        )
          return handleText(file);
        return false;
      }

      function handleImg(file) {
        return new Promise((resolve) => {
          const reader = new FileReader();
          reader.onload = (e) => {
            const src = e.target.result;
            const b64 = src.split(',')[1];
            S.attachments.push({
              id: gid(),
              type: 'image',
              name: file.name,
              mime: file.type || 'image/jpeg',
              b64,
              previewUrl: src,
            });
            resolve(true);
          };
          reader.onerror = () => resolve(false);
          reader.readAsDataURL(file);
        });
      }

      function removeAttachment(id) {
        S.attachments = S.attachments.filter((a) => a.id !== id);
        renderAttachmentBar();
        checkVisionWarn();
        updateSend();
      }

      function clearAttachments() {
        if (!S.attachments.length) return;
        S.attachments = [];
        renderAttachmentBar();
        checkVisionWarn();
        updateSend();
      }

      function renderAttachmentBar() {
        if (!S.attachments.length) {
          D.fBar.classList.remove('on');
          D.fBar.innerHTML = '';
          return;
        }

        D.fBar.classList.add('on');
        D.fBar.innerHTML = S.attachments
          .map((a) => {
            if (a.type === 'image') {
              return `<div class="img-preview"><img src="${a.previewUrl}" alt="${esc(a.name)}"><button class="f-rm" onclick="removeAttachment('${a.id}')"><i class="fa-solid fa-xmark"></i></button></div>`;
            }
            const icon = a.kind === 'pdf' ? 'fa-file-pdf' : 'fa-file-lines';
            const meta =
              a.kind === 'pdf'
                ? `${a.pages || '?'} page${a.pages === 1 ? '' : 's'}`
                : `Text file · ~${(a.text || '').length.toLocaleString()} chars`;
            return `<div class="pdf-preview"><i class="fa-solid ${icon}" style="color:var(--grad1);font-size:22px;"></i><div class="p-info"><strong>${esc(a.name)}</strong><span>${meta}</span></div><button class="f-rm" onclick="removeAttachment('${a.id}')"><i class="fa-solid fa-xmark"></i></button></div>`;
          })
          .join('');
      }

      function checkVisionWarn() {
        const hasImage = S.attachments.some((a) => a.type === 'image');
        if (!hasImage) {
          D.warn.classList.remove('on');
          return;
        }
        if (!isVision(S.model)) {
          D.warnModel.textContent = S.model;
          D.warn.classList.add('on');
        } else D.warn.classList.remove('on');
      }

      let pdfJsLoading = false;
      async function ensurePdfJs() {
        if (window.pdfjsLib) return true;
        if (pdfJsLoading)
          return new Promise((res) => {
            const iv = setInterval(() => {
              if (window.pdfjsLib || window._pdfFailed) {
                clearInterval(iv);
                res(!!window.pdfjsLib);
              }
            }, 100);
          });
        pdfJsLoading = true;
        try {
          const m = await import('./vendor/pdf.min.mjs');
          window.pdfjsLib = m;
          window.pdfjsLib.GlobalWorkerOptions.workerSrc =
            './vendor/pdf.worker.min.mjs';
          return true;
        } catch {
          window._pdfFailed = true;
          return false;
        }
      }

      async function handlePdf(file) {
        const ok = await ensurePdfJs();
        if (!ok) return false;

        try {
          const buf = await file.arrayBuffer();
          const pdf = await window.pdfjsLib.getDocument({ data: buf }).promise;
          let text = '';
          for (let i = 1; i <= pdf.numPages; i++) {
            const pg = await pdf.getPage(i);
            const c = await pg.getTextContent();
            text +=
              `--- Page ${i} ---\n` +
              c.items.map((x) => x.str).join(' ') +
              '\n\n';
          }
          S.attachments.push({
            id: gid(),
            type: 'doc',
            kind: 'pdf',
            name: file.name,
            text: text.trim(),
            pages: pdf.numPages,
          });
          return true;
        } catch (e) {
          return false;
        }
      }

      async function handleText(file) {
        const text = await file.text();
        S.attachments.push({
          id: gid(),
          type: 'doc',
          kind: 'text',
          name: file.name,
          text,
          pages: 0,
        });
        return true;
      }

      // ÔöÇÔöÇ Conversation CRUD ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      function createConv() {
        const c = {
          id: gid(),
          title: 'New chat',
          msgs: [],
          ts: Date.now(),
          model: S.model,
          sys: S.globalSys,
        };
        S.convs.unshift(c);
        save();
        switchConv(c.id);
        renderSB();
        D.inp.focus();
      }

      function delConv(id) {
        S.convs = S.convs.filter((c) => c.id !== id);
        save();
        if (S.curId === id) {
          S.curId = null;
          S.convs.length > 0 ? switchConv(S.convs[0].id) : renderChat();
        }
        renderSB();
        toast('Chat deleted');
      }

      function clearAll() {
        S.convs = [];
        S.curId = null;
        save();
        renderSB();
        renderChat();
        toast('All chats cleared');
      }

      function switchConv(id) {
        S.curId = id;
        const conv = getConv();
        if (conv && conv.model) applyModel(conv.model);
        D.sysTa.value = conv?.sys || '';
        renderChat();
        renderSB();
        updateTitle();
        if (window.innerWidth <= 768) toggleSB(false);
      }
      function getConv() {
        return S.convs.find((c) => c.id === S.curId);
      }

      // ÔöÇÔöÇ Send / Stream to Ollama ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      async function sendMsg(text) {
        if (!S.engineReady || !S.model) {
          toast(S.models.length ? 'Select a model first' : 'No models installed');
          updateSend();
          return;
        }

        if ((!text.trim() && !S.attachments.length) || S.streaming) return;

        let conv = getConv();
        if (!conv) {
          conv = {
            id: gid(),
            title: 'New chat',
            msgs: [],
            ts: Date.now(),
            model: S.model,
            sys: D.sysTa.value.trim(),
          };
          S.convs.unshift(conv);
          S.curId = conv.id;
        } else {
          conv.model = S.model;
          conv.sys = D.sysTa.value.trim();
          // Defensive: heal conv that somehow has no msgs array
          if (!Array.isArray(conv.msgs)) conv.msgs = [];
        }

        if (!conv.model) conv.model = S.model;
        if (!conv.model) {
          toast('No model selected');
          updateSend();
          return;
        }

        const baseText = text.trim();
        const docAttachments = S.attachments.filter((a) => a.type === 'doc');
        const imageAttachments = S.attachments.filter(
          (a) => a.type === 'image',
        );

        let finalText = baseText;
        if (docAttachments.length) {
          const blocks = docAttachments
            .map((a, idx) => {
              const maxChars = 12000;
              const body =
                (a.text || '').length > maxChars
                  ? (a.text || '').slice(0, maxChars) + '\n\n[truncated...]'
                  : a.text || '';
              return `Document ${idx + 1}: "${a.name}"\n\n${body}`;
            })
            .join('\n\n====================\n\n');
          finalText = `Attached document context:\n\n${blocks}\n\n---\nUser: ${baseText || '[no text]'}`;
        }

        const msgObj = {
          id: gid(),
          role: 'user',
          content: finalText,
          displayContent: baseText,
          ts: Date.now(),
        };
        if (imageAttachments.length) {
          msgObj.images = imageAttachments.map((a) => a.b64);
        }
        if (S.attachments.length) {
          msgObj._attachments = S.attachments.map((a) => ({
            id: a.id,
            type: a.type,
            kind: a.kind || null,
            name: a.name,
            mime: a.mime || null,
            b64: a.type === 'image' ? a.b64 : null,
          }));
        }

        conv.msgs.push(msgObj);
        if (conv.msgs.length === 1)
          conv.title =
            text.trim().substring(0, 40) +
            (text.trim().length > 40 ? '...' : '');

        D.inp.value = '';
        D.inp.style.height = 'auto';
        D.iw.classList.remove('ht');
        clearAttachments();
        updateSend();
        save();
        renderChat();
        renderSB();
        updateTitle();
        scrollEnd();

        await streamOllama(conv);
      }

      async function streamOllama(conv) {
        S.streaming = true;
        updateSend();

        const aiMsg = {
          id: gid(),
          role: 'assistant',
          content: '',
          pending: true,
          status: 'Thinking',
          metrics: {
            outputTokens: 0,
            liveTokens: 0,
            tokensPerSecond: 0,
            elapsedMs: 0,
            done: false,
          },
          ts: Date.now(),
          liked: false,
          disliked: false,
        };
        conv.msgs.push(aiMsg);
        renderChat();
        scrollEnd();

        const contentEl = getMsgContentEl(aiMsg.id);
        const statusTimers = [
          setTimeout(() => updatePendingStatus(aiMsg, 'Warming up model'), 1800),
          setTimeout(() => updatePendingStatus(aiMsg, 'Still working'), 7000),
        ];

        S.abort = new AbortController();
        const startedAt = performance.now();
        let apiMsgs = [];
        if (conv.sys) apiMsgs.push({ role: 'system', content: conv.sys });
        conv.msgs.slice(0, -1).forEach((m) => {
          const am = { role: m.role, content: m.content };
          if (m.images) am.images = m.images;
          apiMsgs.push(am);
        });

        let liveRenderTimer = null;
        try {
          const res = await fetch(OLLAMA + '/api/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              model: conv.model,
              messages: apiMsgs,
              stream: true,
              options: {
                temperature:
                  parseFloat(document.getElementById('temp-input')?.value) ||
                  0.7,
              },
            }),
            signal: S.abort.signal,
          });
          if (!res.ok) {
            let errMsg = 'Ollama error ' + res.status;
            try {
              const err = await res.json();
              if (err?.error) errMsg = err.error;
            } catch {
              try {
                const errText = await res.text();
                if (errText.trim()) errMsg = errText.trim();
              } catch {}
            }
            throw new Error(errMsg);
          }

          const reader = res.body.getReader();
          const dec = new TextDecoder();
          if (contentEl) contentEl.innerHTML = '';
          let lastLiveRender = 0;
          const paintLive = (force = false) => {
            if (!contentEl) return;
            const now = performance.now();
            const wait = Math.max(0, 90 - (now - lastLiveRender));
            if (!force && wait > 0) {
              if (!liveRenderTimer) {
                liveRenderTimer = setTimeout(() => {
                  liveRenderTimer = null;
                  paintLive(true);
                }, wait);
              }
              return;
            }
            if (liveRenderTimer) {
              clearTimeout(liveRenderTimer);
              liveRenderTimer = null;
            }
            lastLiveRender = now;
            contentEl.innerHTML = renderStreamingMd(aiMsg.content);
            scrollEnd();
          };

          while (true) {
            const { done, value } = await reader.read();
            if (done) break;
            const chunk = dec.decode(value, { stream: true });
            for (const line of chunk.split('\n')) {
              if (!line.trim()) continue;
              try {
                const p = JSON.parse(line);
                if (p.message?.content) {
                  aiMsg.pending = false;
                  aiMsg.status = '';
                  aiMsg.content += p.message.content;
                  aiMsg.metrics.elapsedMs = performance.now() - startedAt;
                  aiMsg.metrics.liveTokens = estimateTokens(aiMsg.content);
                  aiMsg.metrics.tokensPerSecond = calcTokensPerSecond(
                    aiMsg.metrics.liveTokens,
                    aiMsg.metrics.elapsedMs,
                  );
                  updateMsgStats(aiMsg);
                  paintLive(p.message.content.includes('\n'));
                }
                if (p.done) {
                  aiMsg.metrics.elapsedMs = performance.now() - startedAt;
                  if (Number.isFinite(p.eval_count)) {
                    aiMsg.metrics.outputTokens = p.eval_count;
                    aiMsg.metrics.tokensPerSecond = calcTokensPerSecond(
                      p.eval_count,
                      aiMsg.metrics.elapsedMs,
                    );
                  }
                  if (Number.isFinite(p.prompt_eval_count)) {
                    aiMsg.metrics.promptTokens = p.prompt_eval_count;
                  }
                  if (Number.isFinite(p.total_duration)) {
                    aiMsg.metrics.ollamaMs = p.total_duration / 1000000;
                  }
                  aiMsg.metrics.done = true;
                  updateMsgStats(aiMsg);
                }
              } catch {}
            }
          }
          if (liveRenderTimer) clearTimeout(liveRenderTimer);
          aiMsg.metrics.elapsedMs = performance.now() - startedAt;
          aiMsg.metrics.done = true;
          updateMsgStats(aiMsg);
          if (contentEl) {
            contentEl.innerHTML = renderMd(aiMsg.content);
            contentEl.querySelectorAll('pre code').forEach((b) => {
              if (!b.classList.contains('hljs')) hljs.highlightElement(b);
            });
          }
        } catch (err) {
          aiMsg.pending = false;
          aiMsg.status = '';
          if (err.name === 'AbortError') {
            aiMsg.metrics.elapsedMs = performance.now() - startedAt;
            aiMsg.metrics.outputTokens = estimateTokens(aiMsg.content);
            aiMsg.metrics.tokensPerSecond = calcTokensPerSecond(
              aiMsg.metrics.outputTokens,
              aiMsg.metrics.elapsedMs,
            );
            aiMsg.metrics.stopped = true;
            aiMsg.metrics.done = true;
            updateMsgStats(aiMsg);
            if (aiMsg.content) {
              if (contentEl) contentEl.innerHTML = renderMd(aiMsg.content);
            } else if (contentEl)
              contentEl.innerHTML =
                '<span style="color:var(--t3);font-style:italic;">[Stopped]</span>';
          } else {
            if (contentEl)
              contentEl.innerHTML = `<span style="color:var(--red);">ÔÜá ${esc(err.message)}</span>`;
            conv.msgs.pop();
          }
        } finally {
          if (liveRenderTimer) clearTimeout(liveRenderTimer);
          statusTimers.forEach(clearTimeout);
          aiMsg.pending = false;
          aiMsg.status = '';
          S.streaming = false;
          updateSend();
          save();
          scrollEnd();
          setTimeout(() => D.inp.focus(), 100);
        }
      }

      function stopStream() {
        if (S.abort) S.abort.abort();
      }

      function updatePendingStatus(msg, status) {
        if (!S.streaming || !msg.pending || msg.content) return;
        msg.status = status;
        const el = getMsgContentEl(msg.id);
        if (el) el.innerHTML = renderThinking(status);
      }

      // ÔöÇÔöÇ Markdown ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      function setupMarked() {
        if (typeof marked === 'undefined') return;
        const rdr = new marked.Renderer();
        rdr.code = (code, lang) => {
          const sl = esc(lang || '');
          const dl = sl || 'code';
          let hi = esc(code);
          if (typeof hljs !== 'undefined') {
            try {
              hi =
                lang && hljs.getLanguage(lang)
                  ? hljs.highlight(code, { language: lang }).value
                  : hljs.highlightAuto(code).value;
            } catch {}
          }
          return `<div class="cblk"><div class="cbh"><span>${dl}</span><button class="cbc-btn" onclick="copyCB(this)"><i class="fa-regular fa-copy"></i> Copy</button></div><pre><code class="hljs${sl ? ' language-' + sl : ''}">${hi}</code></pre></div>`;
        };
        marked.setOptions({ gfm: true, breaks: true, renderer: rdr });
      }
      function renderMd(text) {
        if (typeof marked === 'undefined')
          return esc(text).replace(/\n/g, '<br>');
        return marked.parse(text, { breaks: true });
      }

      function renderStreamingMd(text) {
        if (!text) return '';
        let safeText = text;
        const fenceCount = (safeText.match(/(^|\n)```/g) || []).length;
        if (fenceCount % 2 === 1) safeText += '\n```';
        return renderMd(safeText);
      }

      // ÔöÇÔöÇ Rendering ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      function renderSB() {
        D.cvList.innerHTML = S.convs
          .map(
            (c) => `
        <div class="cv ${c.id === S.curId ? 'act' : ''}" onclick="switchConv('${c.id}')">
            <i class="fa-regular fa-message ci"></i>
            <span class="ct">${esc(c.title)}</span>
            <button class="cd" onclick="event.stopPropagation();delConv('${c.id}')" aria-label="Delete chat">
                <i class="fa-solid fa-trash-can"></i>
            </button>
        </div>`,
          )
          .join('');
      }

      function renderChat() {
        const conv = getConv();
        if (!conv || conv.msgs.length === 0) {
          D.welcome.style.display = 'flex';
          D.msgs.classList.remove('on');
          D.msgs.innerHTML = '';
          D.tbTitle.textContent = '';
          return;
        }
        D.welcome.style.display = 'none';
        D.msgs.classList.add('on');
        D.tbTitle.textContent = conv.title;

        D.msgs.innerHTML = conv.msgs
          .map((msg, i) =>
            msg.role === 'user'
              ? renderUser(msg)
              : renderAI(msg, i === conv.msgs.length - 1),
          )
          .join('');

        D.msgs.querySelectorAll('pre code:not(.hljs)').forEach((b) => {
          hljs.highlightElement(b);
        });
        scrollEnd();
      }

      function renderUser(msg) {
        let media = '';
        const atts = Array.isArray(msg._attachments) ? msg._attachments : [];
        atts.forEach((a) => {
          if (a.type === 'image' && a.b64) {
            media += `<img class="msg-img" src="data:${a.mime || 'image/jpeg'};base64,${a.b64}">`;
            return;
          }
          if (a.type === 'doc') {
            const icon = a.kind === 'pdf' ? 'fa-file-pdf' : 'fa-file-lines';
            media += `<div class="msg-pdf-pill"><i class="fa-solid ${icon}" style="color:var(--grad1)"></i>${esc(a.name || 'Document')}</div>`;
          }
        });

        let rawTxt = msg.displayContent || msg.content;
        if (!msg.displayContent && rawTxt.includes('\n\n---\nUser: ')) {
          rawTxt = rawTxt.split('\n\n---\nUser: ').pop() || '';
        }
        const textBubble = rawTxt.trim()
          ? `<div class="ub"><div class="mt">${esc(rawTxt)}</div></div>`
          : '';
        const mediaEl = media ? `<div class="usr-attach">${media}</div>` : '';
        return `<div class="mr usr"><div class="mc">${textBubble}${mediaEl}</div><div class="ma u">U</div></div>`;
      }

      function renderAI(msg, isLast) {
        const isWaiting = S.streaming && isLast && !msg.content;
        const parsed = isWaiting
          ? renderThinking(msg.status || 'Thinking')
          : S.streaming && isLast
            ? renderStreamingMd(msg.content)
            : renderMd(msg.content);
        const actions =
          !S.streaming || !isLast
            ? `
        <div class="mact">
            <button class="mab" onclick="copyMsg('${msg.id}')" title="Copy"><i class="fa-regular fa-copy"></i></button>
            <button class="mab ${msg.liked ? 'lk' : ''}" onclick="rateMsg('${msg.id}','like')" title="Good response"><i class="fa-regular fa-thumbs-up"></i></button>
            <button class="mab ${msg.disliked ? 'dlk' : ''}" onclick="rateMsg('${msg.id}','dislike')" title="Bad response"><i class="fa-regular fa-thumbs-down"></i></button>
        </div>`
            : '';

        return `<div class="mr" data-msg-id="${msg.id}">
        <div class="ma ai"><svg viewBox="0 0 24 24"><path d="M12 2L14.09 8.26L21 9.27L16 14.14L17.18 21.02L12 17.77L6.82 21.02L8 14.14L3 9.27L9.91 8.26L12 2Z" fill="white"/></svg></div>
        <div class="mc"><div class="mt">${parsed || ''}</div>${renderMsgStats(msg)}${actions}</div>
    </div>`;
      }

      function getMsgContentEl(id) {
        return D.msgs.querySelector(`[data-msg-id="${id}"] .mt`);
      }

      function getMsgStatsEl(id) {
        return D.msgs.querySelector(`[data-msg-id="${id}"] .msg-meta`);
      }

      function updateMsgStats(msg) {
        const el = getMsgStatsEl(msg.id);
        if (el) el.outerHTML = renderMsgStats(msg);
      }

      function renderThinking(label) {
        return `<div class="think"><span>${esc(label)}</span><div class="typ"><span></span><span></span><span></span></div></div>`;
      }

      function renderMsgStats(msg) {
        const m = msg.metrics;
        if (!m) return '';
        const parts = [];
        if (!m.done) {
          parts.push(`${formatRate(m.tokensPerSecond)} tok/s`);
        } else {
          parts.push(`${Number(m.outputTokens || 0).toLocaleString()} tokens`);
        }
        if (m.done && Number.isFinite(m.elapsedMs)) {
          parts.push(formatDuration(m.elapsedMs));
        }
        if (m.stopped) parts.push('stopped');
        const live = !m.done ? ' live' : '';
        return `<div class="msg-meta${live}">${parts.map(esc).join('<span class="dot"></span>')}</div>`;
      }

      function estimateTokens(text) {
        if (!text) return 0;
        const chunks = text.match(/[\p{L}\p{N}_]+|[^\s]/gu) || [];
        return chunks.length;
      }

      function calcTokensPerSecond(tokens, elapsedMs) {
        if (!Number.isFinite(tokens) || !Number.isFinite(elapsedMs) || elapsedMs <= 0)
          return 0;
        return tokens / (elapsedMs / 1000);
      }

      function formatRate(rate) {
        if (!Number.isFinite(rate) || rate <= 0) return '0.0';
        return rate < 10 ? rate.toFixed(1) : rate.toFixed(0);
      }

      function formatDuration(ms) {
        if (!Number.isFinite(ms)) return '';
        if (ms < 1000) return `${Math.max(1, Math.round(ms))} ms`;
        if (ms < 60000) return `${(ms / 1000).toFixed(ms < 10000 ? 1 : 0)} s`;
        const minutes = Math.floor(ms / 60000);
        const seconds = Math.round((ms % 60000) / 1000);
        return `${minutes}m ${seconds}s`;
      }

      // ÔöÇÔöÇ Actions ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      function copyMsg(id) {
        const m = getConv()?.msgs.find((x) => x.id === id);
        if (m)
          navigator.clipboard.writeText(m.content).then(() => toast('Copied'));
      }
      function rateMsg(id, r) {
        const m = getConv()?.msgs.find((x) => x.id === id);
        if (!m) return;
        if (r === 'like') {
          m.liked = !m.liked;
          m.disliked = false;
        } else {
          m.disliked = !m.disliked;
          m.liked = false;
        }
        save();
        renderChat();
      }
      function copyCB(btn) {
        const code = btn.closest('.cblk').querySelector('code').innerText;
        navigator.clipboard.writeText(code).then(() => {
          btn.innerHTML = '<i class="fa-solid fa-check"></i> Copied';
          setTimeout(
            () => (btn.innerHTML = '<i class="fa-regular fa-copy"></i> Copy'),
            2000,
          );
        });
      }

      function applyTheme(t) {
        S.theme = t;
        document.documentElement.setAttribute('data-theme', t);
        localStorage.setItem('g-theme', t);
        const ic = t === 'dark' ? 'fa-moon' : 'fa-sun';
        D.thTop.querySelector('i').className = 'fa-solid ' + ic;
        D.thSb.querySelector('i').className = 'fa-solid ' + ic;
      }
      function toggleTheme() {
        applyTheme(S.theme === 'dark' ? 'light' : 'dark');
      }
      function toggleSB(force) {
        S.sbOpen = force !== undefined ? force : !S.sbOpen;
        D.sb.classList.toggle('off', !S.sbOpen);
        D.ov.classList.toggle('on', S.sbOpen && window.innerWidth <= 768);
      }

      function updateSend() {
        const has = D.inp.value.trim().length > 0 || S.attachments.length > 0;
        const canSend = has && S.engineReady && !!S.model;
        D.iw.classList.toggle('ht', has);
        if (S.streaming) {
          D.send.className = 'ib2 stbtn';
          D.send.innerHTML = '<i class="fa-solid fa-stop"></i>';
          D.send.disabled = false;
        } else {
          D.send.className = 'ib2 sbtn' + (canSend ? ' on' : '');
          D.send.innerHTML = '<i class="fa-solid fa-arrow-up"></i>';
          D.send.disabled = !canSend;
        }
      }
      function autoResize() {
        D.inp.style.height = 'auto';
        D.inp.style.height = Math.min(D.inp.scrollHeight, 200) + 'px';
      }
      function scrollEnd() {
        D.chat.scrollTop = D.chat.scrollHeight;
      }
      function updateTitle() {
        D.tbTitle.textContent = getConv()?.title || '';
      }
      function toast(msg) {
        const t = document.createElement('div');
        t.className = 'toast';
        t.textContent = msg;
        D.toasts.appendChild(t);
        setTimeout(() => t.remove(), 2600);
      }

      // ÔöÇÔöÇ Persistence ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      let saveTimer = null;
      function save() {
        if (IS_SERVED) {
          clearTimeout(saveTimer);
          saveTimer = setTimeout(
            () =>
              fetch('/api/chats', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(S.convs),
              }).catch(() => {}),
            800,
          );
        } else {
          try {
            localStorage.setItem('g-convs', JSON.stringify(S.convs));
          } catch (e) {}
        }
      }
      async function load() {
        if (IS_SERVED) {
          try {
            const r = await fetch('/api/chats');
            if (r.ok) S.convs = await r.json();
          } catch {}
        } else {
          try {
            const d = localStorage.getItem('g-convs');
            if (d) S.convs = JSON.parse(d);
          } catch (e) {
            S.convs = [];
          }
        }
        // ÔöÇÔöÇ Sanitize: heal any conversation that is missing required fields
        // (handles old schema that used `messages` instead of `msgs`, or any
        //  partial/corrupted entries that snuck in from a previous version)
        S.convs = (Array.isArray(S.convs) ? S.convs : []).map((c) => ({
          id: c.id || gid(),
          title: c.title || 'Untitled',
          ts: c.ts || Date.now(),
          model: c.model || '',
          sys: c.sys || '',
          // migrate old `messages` key ÔåÆ `msgs`; fall back to []
          msgs: Array.isArray(c.msgs)
            ? c.msgs
            : Array.isArray(c.messages)
              ? c.messages
              : [],
        }));
      }

      function gid() {
        return (
          Date.now().toString(36) + Math.random().toString(36).substring(2, 8)
        );
      }
      function esc(t) {
        const d = document.createElement('div');
        d.textContent = t;
        return d.innerHTML;
      }

      // ÔöÇÔöÇ Events ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      function bind() {
        D.send.addEventListener('click', () =>
          S.streaming ? stopStream() : sendMsg(D.inp.value),
        );
        D.inp.addEventListener('input', () => {
          autoResize();
          updateSend();
        });
        D.inp.addEventListener('keydown', (e) => {
          if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            if (!S.streaming) sendMsg(D.inp.value);
          }
          if (e.key === 'Escape' && S.streaming) stopStream();
        });
        D.nc.addEventListener('click', () => {
          if (!S.streaming) createConv();
        });
        D.sbTog.addEventListener('click', () => toggleSB());
        D.ov.addEventListener('click', () => toggleSB(false));
        D.thTop.addEventListener('click', toggleTheme);
        D.thSb.addEventListener('click', toggleTheme);
        D.ca.addEventListener('click', () => {
          if (!S.streaming) clearAll();
        });
        D.att.addEventListener('click', () => D.fInp.click());
        D.fInp.addEventListener('change', async (e) => {
          await handleAttach(e.target.files);
          e.target.value = '';
        });
        $$('.sc').forEach((c) =>
          c.addEventListener('click', () => {
            const p = c.dataset.prompt;
            if (p) sendMsg(p);
          }),
        );

      // Panel toggles
      D.modelBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        toggleModelMenu();
        D.sysPanel.classList.remove('open');
      });
      D.sysBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        D.sysPanel.classList.toggle('open');
        toggleModelMenu(false);
      });
      D.logModeSel.addEventListener('change', saveLogMode);
      D.sysSet.addEventListener('click', saveGlobalPrompt);
      D.sysClr.addEventListener('click', clearGlobalPrompt);

        document.addEventListener('click', (e) => {
          if (!D.modelDd.contains(e.target)) toggleModelMenu(false);
          if (!D.sysPanel.contains(e.target) && !D.sysBtn.contains(e.target))
            D.sysPanel.classList.remove('open');
        });
        window.addEventListener('resize', () => {
          if (window.innerWidth <= 768 && S.sbOpen) toggleSB(false);
        });

        // Drag-and-drop (image / PDF / text files)
        const mainEl = document.getElementById('main');
        mainEl.addEventListener('dragover', (e) => {
          e.preventDefault();
          mainEl.classList.add('drag-over');
        });
        mainEl.addEventListener('dragleave', () =>
          mainEl.classList.remove('drag-over'),
        );
        mainEl.addEventListener('drop', (e) => {
          e.preventDefault();
          mainEl.classList.remove('drag-over');
          handleAttach(e.dataTransfer.files);
        });
      }

      // ÔöÇÔöÇ Start ÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇÔöÇ
      init();
    </script>
  </body>
</html>
