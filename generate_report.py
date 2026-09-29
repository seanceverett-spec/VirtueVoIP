import re

md_file = "/data/data/com.termux/files/home/projects/swarm_sim/output/EPOCH_III_FINAL.md"
html_out = "/data/data/com.termux/files/home/storage/downloads/EPOCH_III_TECHNICAL_REPORT.html"

with open(md_file, "r") as f:
    text = f.read()

html_body = []
lines = text.split("\n")
in_table = False

for line in lines:
    line = line.strip()
    if not line:
        if in_table:
            html_body.append("</tbody></table>")
            in_table = False
        continue
    if line.startswith("# "):
        html_body.append(f"<h1>{line[2:]}</h1>")
    elif line.startswith("## "):
        html_body.append(f"<h2>{line[3:]}</h2>")
    elif line.startswith("### "):
        html_body.append(f"<h3>{line[4:]}</h3>")
    elif line.startswith("---"):
        html_body.append("<hr/>")
    elif line.startswith("|"):
        cols = [c.strip() for c in line.strip("|").split("|")]
        if all(re.match(r"^:?-+:?$", c) for c in cols):
            continue  # delimiter line
        if not in_table:
            in_table = True
            headers = "".join([f"<th>{c}</th>" for c in cols])
            html_body.append(f"<table><thead><tr>{headers}</tr></thead><tbody>")
        else:
            cells = "".join([f"<td>{c}</td>" for c in cols])
            html_body.append(f"<tr>{cells}</tr>")
    elif re.match(r"^\d+\.\s", line):
        clean = re.sub(r"^\d+\.\s", "", line)
        clean = re.sub(r"\*\*(.*?)\*\*", r"<strong>\1</strong>", clean)
        html_body.append(f"<p class='num-item'>• {clean}</p>")
    else:
        if in_table:
            html_body.append("</tbody></table>")
            in_table = False
        line_clean = re.sub(r"\*\*(.*?)\*\*", r"<strong>\1</strong>", line)
        line_clean = re.sub(r"`(.*?)`", r"<code>\1</code>", line_clean)
        line_clean = line_clean.replace(r"$\le", "&le;").replace(r"\le", "&le;").replace(r"$\ge", "&ge;").replace(r"\ge", "&ge;").replace("$", "")
        html_body.append(f"<p>{line_clean}</p>")

if in_table:
    html_body.append("</tbody></table>")

full_html = f"""<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Epoch III Technical Report</title>
<style>
  @page {{
    size: A4 portrait;
    margin: 18mm 15mm;
  }}
  body {{
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Arial, sans-serif;
    color: #0f172a;
    background: #ffffff;
    max-width: 820px;
    margin: 0 auto;
    padding: 24px;
    line-height: 1.55;
    font-size: 13.5px;
  }}
  h1 {{
    font-size: 22px;
    font-weight: 800;
    color: #0f172a;
    border-bottom: 3px solid #2563eb;
    padding-bottom: 8px;
    margin-top: 0;
  }}
  h2 {{
    font-size: 16px;
    font-weight: 700;
    color: #1e293b;
    border-bottom: 1.5px solid #e2e8f0;
    padding-bottom: 5px;
    margin-top: 24px;
  }}
  h3 {{
    font-size: 14px;
    color: #334155;
    margin-top: 16px;
    margin-bottom: 6px;
  }}
  p {{
    margin: 8px 0;
    color: #334155;
  }}
  .num-item {{
    margin: 4px 0 4px 12px;
  }}
  table {{
    width: 100%;
    border-collapse: collapse;
    margin: 16px 0;
    font-size: 12.5px;
  }}
  th, td {{
    border: 1px solid #cbd5e1;
    padding: 8px 10px;
    text-align: left;
  }}
  th {{
    background-color: #f1f5f9;
    font-weight: 700;
    color: #0f172a;
  }}
  tr:nth-child(even) {{
    background-color: #f8fafc;
  }}
  code {{
    background-color: #e2e8f0;
    padding: 2px 6px;
    border-radius: 4px;
    font-family: monospace;
    font-size: 12px;
  }}
  hr {{
    border: none;
    border-top: 1px solid #e2e8f0;
    margin: 20px 0;
  }}
</style>
</head>
<body>
{"".join(html_body)}
</body>
</html>"""

with open(html_out, "w") as f:
    f.write(full_html)
print("Complete: Styled HTML exported directly to ~/storage/downloads/EPOCH_III_TECHNICAL_REPORT.html")
